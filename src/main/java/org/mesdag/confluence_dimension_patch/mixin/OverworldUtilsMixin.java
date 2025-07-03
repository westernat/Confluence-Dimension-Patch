package org.mesdag.confluence_dimension_patch.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.confluence.mod.common.init.ModTags;
import org.confluence.mod.util.OverworldUtils;
import org.mesdag.confluence_dimension_patch.common.OtherWorld;
import org.mesdag.confluence_dimension_patch.mixed.IDimensionAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Mixin(value = OverworldUtils.class, remap = false)
public abstract class OverworldUtilsMixin {
    @Inject(method = "dimension", at = @At("HEAD"), cancellable = true)
    private static void replace(CallbackInfoReturnable<ResourceKey<Level>> cir) {
        cir.setReturnValue(OtherWorld.LEVEL);
    }

    @Inject(method = "replaceBiome", at = @At("HEAD"), cancellable = true)
    private static void unApply(MultiNoiseBiomeSource biomeSource, int x, int y, int z, CallbackInfoReturnable<Holder<Biome>> cir, Supplier<List<Holder<Biome>>> jungleGetter, Supplier<Pair<Holder<Biome>, Holder<Biome>>> biomePairGetter, Function<RegistryAccess, Holder<Biome>> protectionFactory, CallbackInfo ci) {
        if (!((IDimensionAccessor) biomeSource).confluence_dimension_patch$allows()) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server != null && cir.getReturnValue().is(ModTags.Biomes.IS_CONFLUENCE)) {
                cir.setReturnValue(protectionFactory.apply(server.registryAccess()));
            }
            ci.cancel();
        }
    }

    @Inject(method = "replaceTree", at = @At("HEAD"), cancellable = true)
    private static void skip(FeaturePlaceContext<TreeConfiguration> context, CallbackInfoReturnable<Boolean> cir, CallbackInfo ci) {
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
