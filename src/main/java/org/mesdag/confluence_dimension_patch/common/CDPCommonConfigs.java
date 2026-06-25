package org.mesdag.confluence_dimension_patch.common;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class CDPCommonConfigs {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue INITIAL_TERRARIUM = BUILDER.define("initialTerrarium", true);
    public static final ModConfigSpec.BooleanValue ALLOW_NETHER_MOB_SPAWNING = BUILDER
            .comment("Allows Confluence and TerraEntity mobs to spawn in the Nether.")
            .define("allowNetherMobSpawning", true);
    public static final ModConfigSpec.BooleanValue ALLOW_END_MOB_SPAWNING = BUILDER
            .comment("Allows Confluence and TerraEntity mobs to spawn in the End.")
            .define("allowEndMobSpawning", true);
    public static final ModConfigSpec.BooleanValue ALLOW_NETHER_BIOME_GENERATION = BUILDER
            .comment("Allows Confluence Nether biomes and terrain features to generate in the vanilla Nether.")
            .define("allowNetherBiomeGeneration", true);
    public static final ModConfigSpec.BooleanValue ALLOW_END_BIOME_GENERATION = BUILDER
            .comment("Allows Confluence End biomes and terrain features to generate in the vanilla End.")
            .define("allowEndBiomeGeneration", true);
    public static final ModConfigSpec.BooleanValue ALLOW_NETHER_STRUCTURE_GENERATION = BUILDER
            .comment("Allows Confluence structures to generate in the vanilla Nether.")
            .define("allowNetherStructureGeneration", true);
    public static final ModConfigSpec.BooleanValue ALLOW_END_STRUCTURE_GENERATION = BUILDER
            .comment("Allows Confluence structures to generate in the End.")
            .define("allowEndStructureGeneration", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean allowsConfluenceMobSpawning(ResourceKey<Level> dimension) {
        if (OtherWorld.LEVEL.equals(dimension)) {
            return true;
        }
        if (Level.NETHER.equals(dimension)) {
            return ALLOW_NETHER_MOB_SPAWNING.get();
        }
        if (Level.END.equals(dimension)) {
            return ALLOW_END_MOB_SPAWNING.get();
        }
        return false;
    }

    public static boolean allowsConfluenceBiomeGeneration(ResourceKey<Level> dimension) {
        if (OtherWorld.LEVEL.equals(dimension)) {
            return true;
        }
        if (Level.NETHER.equals(dimension)) {
            return ALLOW_NETHER_BIOME_GENERATION.get();
        }
        if (Level.END.equals(dimension)) {
            return ALLOW_END_BIOME_GENERATION.get();
        }
        return false;
    }

    public static boolean allowsConfluenceStructureGeneration(ResourceKey<Level> dimension) {
        if (OtherWorld.LEVEL.equals(dimension)) {
            return true;
        }
        if (Level.NETHER.equals(dimension)) {
            return ALLOW_NETHER_STRUCTURE_GENERATION.get();
        }
        if (Level.END.equals(dimension)) {
            return ALLOW_END_STRUCTURE_GENERATION.get();
        }
        return false;
    }
}
