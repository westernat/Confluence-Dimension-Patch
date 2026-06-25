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
            .comment("Allows Confluence Nether biomes to generate in the vanilla Nether.")
            .define("allowNetherBiomeGeneration", true);
    public static final ModConfigSpec.BooleanValue ALLOW_END_BIOME_GENERATION = BUILDER
            .comment("Allows Confluence End biomes to generate in the vanilla End.")
            .define("allowEndBiomeGeneration", true);
    public static final ModConfigSpec.BooleanValue TRACE_OTHERWORLD_TICK_WHEN_UNLOADED = BUILDER
            .comment("Logs slow Confluence tick paths while the OtherWorld has no players. Intended for TPS diagnostics.")
            .define("traceOtherworldTickWhenUnloaded", false);
    public static final ModConfigSpec.IntValue OTHERWORLD_TICK_TRACE_THRESHOLD_MICROS = BUILDER
            .comment("Minimum elapsed time in microseconds before an unloaded OtherWorld tick trace is logged.")
            .defineInRange("otherworldTickTraceThresholdMicros", 2000, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue OTHERWORLD_TICK_TRACE_INTERVAL_TICKS = BUILDER
            .comment("Minimum game ticks between repeated unloaded OtherWorld tick trace logs for the same path.")
            .defineInRange("otherworldTickTraceIntervalTicks", 100, 1, Integer.MAX_VALUE);

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
}
