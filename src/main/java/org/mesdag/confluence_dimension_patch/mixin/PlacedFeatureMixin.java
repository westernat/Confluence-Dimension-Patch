package org.mesdag.confluence_dimension_patch.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.neoforged.neoforge.common.util.TriState;
import org.confluence.mod.Confluence;
import org.mesdag.confluence_dimension_patch.common.CDPCommonConfigs;
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
    private static final String CDP$MINECRAFT = "minecraft";

    @Unique
    private TriState cdp$skipWhenTerrainDisallowed = TriState.DEFAULT;

    @Inject(method = "placeWithContext", at = @At("HEAD"), cancellable = true)
    private void skip(PlacementContext context, RandomSource source, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        ResourceKey<Level> dimension = context.getLevel().getLevel().dimension();
        if (CDPCommonConfigs.allowsConfluenceBiomeGeneration(dimension)) {
            return;
        }
        if (cdp$skipWhenTerrainDisallowed.isDefault()) {
            cdp$skipWhenTerrainDisallowed = cdp$shouldSkipWhenTerrainDisallowed() ? TriState.TRUE : TriState.FALSE;
        }
        if (cdp$skipWhenTerrainDisallowed.isTrue()) {
            cir.setReturnValue(false);
        }
    }

    @Unique
    private boolean cdp$shouldSkipWhenTerrainDisallowed() {
        var key = feature.getKey();
        if (key != null && Confluence.MODID.equals(key.location().getNamespace())) {
            return true;
        }

        ConfiguredFeature<?, ?> configuredFeature = feature.value();
        if (cdp$isGeneratedOre(configuredFeature) || cdp$isNewTree(configuredFeature)) {
            return key == null || !CDP$MINECRAFT.equals(key.location().getNamespace());
        }
        return false;
    }

    @Unique
    private static boolean cdp$isGeneratedOre(ConfiguredFeature<?, ?> configuredFeature) {
        return configuredFeature.feature() instanceof OreFeature;
    }

    @Unique
    private static boolean cdp$isNewTree(ConfiguredFeature<?, ?> configuredFeature) {
        return configuredFeature.config() instanceof TreeConfiguration;
    }
}
