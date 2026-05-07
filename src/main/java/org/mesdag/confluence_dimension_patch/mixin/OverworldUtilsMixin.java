package org.mesdag.confluence_dimension_patch.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.confluence.mod.common.init.ModTags;
import org.confluence.mod.util.OverworldUtils;
import org.mesdag.confluence_dimension_patch.common.OtherWorld;
import org.mesdag.confluence_dimension_patch.mixed.IDimensionAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = OverworldUtils.class, remap = false)
public abstract class OverworldUtilsMixin {
    @Shadow
    private static boolean uninitialized;

    @Shadow
    private static Holder<Biome> plains;

    @Inject(method = "dimension", at = @At("HEAD"), cancellable = true)
    private static void replace(CallbackInfoReturnable<ResourceKey<Level>> cir) {
        cir.setReturnValue(OtherWorld.LEVEL);
    }

    @Inject(method = "replaceBiome", at = @At("HEAD"), cancellable = true)
    private static void unApply(CallbackInfo ci, @Local(argsOnly = true) MultiNoiseBiomeSource biomeSource, @Local(argsOnly = true) CallbackInfoReturnable<Holder<Biome>> cir) {
        if (!uninitialized && IDimensionAccessor.of(biomeSource).cdp$isOverworld()) {
            if (cir.getReturnValue().is(ModTags.Biomes.IS_CONFLUENCE)) {
                cir.setReturnValue(plains);
            }
            ci.cancel();
        }
    }

    @Inject(method = "replaceTree", at = @At("HEAD"), cancellable = true)
    private static void skip(CallbackInfo ci, @Local(argsOnly = true) FeaturePlaceContext<TreeConfiguration> context) {
        if (context.level().getLevel().dimension() != OtherWorld.LEVEL) {
            ci.cancel();
        }
    }

    @Inject(method = "replaceLogBoulder", at = @At("HEAD"), cancellable = true)
    private static void skip(WorldGenLevel instance, BlockPos blockPos, BlockState blockState, int i, Operation<Boolean> original, CallbackInfoReturnable<Boolean> cir) {
        if (instance.getLevel().dimension() != OtherWorld.LEVEL) {
            cir.setReturnValue(original.call(instance, blockPos, blockState, i));
        }
    }
}
