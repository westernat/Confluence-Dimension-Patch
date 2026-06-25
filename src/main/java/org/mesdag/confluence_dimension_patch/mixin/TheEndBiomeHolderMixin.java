package org.mesdag.confluence_dimension_patch.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import org.confluence.mod.common.worldgen.TheEndBiomeHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(value = TheEndBiomeHolder.class, remap = false)
public abstract class TheEndBiomeHolderMixin {
    @Inject(method = "addConfluenceBiomes", at = @At("HEAD"), cancellable = true)
    private static void skipConfluenceEndBiomes(CallbackInfoReturnable<Stream<Holder<Biome>>> cir, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "replaceBiome", at = @At("HEAD"), cancellable = true)
    private static void skipConfluenceEndBiomeReplacement(int x, int y, int z, Climate.Sampler sampler, CallbackInfoReturnable<Holder<Biome>> cir, CallbackInfo ci) {
        ci.cancel();
    }
}
