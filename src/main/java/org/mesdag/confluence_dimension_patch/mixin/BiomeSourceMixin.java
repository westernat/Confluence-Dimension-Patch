package org.mesdag.confluence_dimension_patch.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.BiomeSource;
import org.mesdag.confluence_dimension_patch.mixed.IDimensionAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BiomeSource.class)
public abstract class BiomeSourceMixin implements IDimensionAccessor {
    @Unique
    private ResourceKey<Level> cdp$dimension = Level.OVERWORLD;

    @Override
    public void cdp$setDimension(ResourceKey<Level> dimension) {
        this.cdp$dimension = dimension;
    }

    @Override
    public ResourceKey<Level> cdp$getDimension() {
        return cdp$dimension;
    }
}
