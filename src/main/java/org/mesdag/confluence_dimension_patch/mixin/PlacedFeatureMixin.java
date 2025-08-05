package org.mesdag.confluence_dimension_patch.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.neoforged.neoforge.common.util.TriState;
import org.confluence.mod.Confluence;
import org.mesdag.confluence_dimension_patch.mixed.IDimensionAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlacedFeature.class)
public abstract class PlacedFeatureMixin {
    @Shadow
    @Final
    private Holder<ConfiguredFeature<?, ?>> feature;

    @Unique
    private final TriState[] confluence_dimension_patch$cache = new TriState[]{TriState.DEFAULT, TriState.DEFAULT};

    @Inject(method = "placeWithContext", at = @At("HEAD"), cancellable = true)
    private void skip(PlacementContext context, RandomSource source, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        int index = IDimensionAccessor.of(context.generator().getBiomeSource()).confluence_dimension_patch$isNotOverworld() ? 1 : 0;
        if (confluence_dimension_patch$cache[index].isDefault() && feature.getKey() != null) {
            if (index != 1 && Confluence.MODID.equals(feature.getKey().location().getNamespace())) {
                confluence_dimension_patch$cache[index] = TriState.FALSE;
            } else {
                confluence_dimension_patch$cache[index] = TriState.TRUE;
            }
        }
        if (confluence_dimension_patch$cache[index].isFalse()) {
            cir.setReturnValue(false);
        }
    }
}
