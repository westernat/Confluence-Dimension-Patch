package org.mesdag.confluence_dimension_patch.common.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import org.mesdag.confluence_dimension_patch.ConfluenceDimensionPatch;

public final class OtherWorld {
    public static final ResourceKey<DimensionType> DIMENSION_TYPE = ResourceKey.create(Registries.DIMENSION_TYPE, ConfluenceDimensionPatch.asResource("otherworld"));
    public static final ResourceKey<LevelStem> LEVEL_STEM = ResourceKey.create(Registries.LEVEL_STEM, ConfluenceDimensionPatch.asResource("otherworld"));
    public static final ResourceKey<Level> LEVEL = Registries.levelStemToLevel(LEVEL_STEM);
}
