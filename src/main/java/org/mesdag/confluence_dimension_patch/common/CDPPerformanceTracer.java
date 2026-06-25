package org.mesdag.confluence_dimension_patch.common;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.mesdag.confluence_dimension_patch.ConfluenceDimensionPatch;

import java.util.HashMap;
import java.util.Map;

public final class CDPPerformanceTracer {
    private static final long TRACE_THRESHOLD_MICROS = 2000L;
    private static final int TRACE_INTERVAL_TICKS = 100;
    private static final ThreadLocal<Long> OTHERWORLD_LEVEL_TICK_START_NANOS = new ThreadLocal<>();
    private static final Map<String, Long> LAST_TRACE_LOG_GAME_TIMES = new HashMap<>();

    private CDPPerformanceTracer() {}

    public static void beginOtherworldLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel() instanceof ServerLevel level && shouldTraceOtherworldLevel(level)) {
            OTHERWORLD_LEVEL_TICK_START_NANOS.set(System.nanoTime());
        } else {
            OTHERWORLD_LEVEL_TICK_START_NANOS.remove();
        }
    }

    public static void endOtherworldLevelTick(LevelTickEvent.Post event) {
        Long startNanos = OTHERWORLD_LEVEL_TICK_START_NANOS.get();
        if (startNanos == null) {
            return;
        }
        try {
            if (event.getLevel() instanceof ServerLevel level && shouldTraceOtherworldLevel(level)) {
                logIfSlow(
                        "confluence level tick",
                        startNanos,
                        level.getGameTime(),
                        true,
                        level.players().size()
                );
            }
        } finally {
            OTHERWORLD_LEVEL_TICK_START_NANOS.remove();
        }
    }

    public static long beginOtherworldPath(ServerLevel level) {
        return shouldTraceOtherworldLevel(level) ? System.nanoTime() : 0L;
    }

    public static void endOtherworldPath(String path, long startNanos, ServerLevel level) {
        if (startNanos == 0L) {
            return;
        }
        logIfSlow(path, startNanos, level.getGameTime(), true, level.players().size());
    }

    public static long beginPathServiceTick() {
        return shouldTraceServerPath() ? System.nanoTime() : 0L;
    }

    public static void endPathServiceTick(long startNanos) {
        if (startNanos == 0L) {
            return;
        }
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return;
        }
        ServerLevel otherworld = server.getLevel(OtherWorld.LEVEL);
        boolean loaded = otherworld != null;
        int players = loaded ? otherworld.players().size() : 0;
        long gameTime = loaded ? otherworld.getGameTime() : server.overworld().getGameTime();
        logIfSlow(
                "confluence path service tick",
                startNanos,
                gameTime,
                loaded,
                players
        );
    }

    private static boolean shouldTraceOtherworldLevel(ServerLevel level) {
        return OtherWorld.LEVEL.equals(level.dimension())
                && level.players().isEmpty();
    }

    private static boolean shouldTraceServerPath() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) {
            return false;
        }
        ServerLevel otherworld = server.getLevel(OtherWorld.LEVEL);
        return otherworld == null || otherworld.players().isEmpty();
    }

    private static void logIfSlow(String path, long startNanos, long gameTime, boolean otherworldLoaded, int players) {
        long elapsedMicros = (System.nanoTime() - startNanos) / 1000L;
        if (elapsedMicros < TRACE_THRESHOLD_MICROS) {
            return;
        }
        long lastLogGameTime = LAST_TRACE_LOG_GAME_TIMES.getOrDefault(path, Long.MIN_VALUE);
        if (lastLogGameTime != Long.MIN_VALUE && gameTime - lastLogGameTime < TRACE_INTERVAL_TICKS) {
            return;
        }
        ConfluenceDimensionPatch.LOGGER.warn(
                "[CDP-TPS-TRACE] {} took {} us while OtherWorld loaded={}, players={}",
                path,
                elapsedMicros,
                otherworldLoaded,
                players
        );
        LAST_TRACE_LOG_GAME_TIMES.put(path, gameTime);
    }
}
