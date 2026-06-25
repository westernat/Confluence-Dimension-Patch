package org.mesdag.confluence_dimension_patch.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import org.confluence.mod.common.block.functional.network.PathService;
import org.confluence.mod.common.data.saved.BossDelaySpawner;
import org.confluence.mod.common.data.saved.HardmodeConvertor;
import org.confluence.mod.common.data.saved.MeteoriteTracker;
import org.confluence.mod.common.event.game.TickEvents;
import org.confluence.mod.common.gameevent.GameEventSystem;
import org.mesdag.confluence_dimension_patch.common.CDPPerformanceTracer;
import org.mesdag.confluence_dimension_patch.common.OtherWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TickEvents.class, remap = false)
public abstract class TickEventsMixin {
    @Inject(method = "levelTick$Post", at = @At("HEAD"), cancellable = true)
    private static void cdp$beginOtherworldTickTrace(LevelTickEvent.Post event, CallbackInfo ci) {
        if (event.getLevel() instanceof ServerLevel level && cdp$shouldSkipEmptyOtherworldTick(level)) {
            ci.cancel();
            return;
        }
        CDPPerformanceTracer.beginOtherworldLevelTick(event);
    }

    @Inject(method = "levelTick$Post", at = @At("RETURN"))
    private static void cdp$endOtherworldTickTrace(LevelTickEvent.Post event, CallbackInfo ci) {
        CDPPerformanceTracer.endOtherworldLevelTick(event);
    }

    @WrapOperation(method = "levelTick$Post", at = @At(value = "INVOKE", target = "Lorg/confluence/mod/common/gameevent/GameEventSystem;tick()V"))
    private static void cdp$traceGameEventSystemTick(GameEventSystem instance, Operation<Void> original, LevelTickEvent.Post event) {
        ServerLevel level = (ServerLevel) event.getLevel();
        long startNanos = CDPPerformanceTracer.beginOtherworldPath(level);
        try {
            original.call(instance);
        } finally {
            CDPPerformanceTracer.endOtherworldPath("game event system tick", startNanos, level);
        }
    }

    @WrapOperation(method = "levelTick$Post", at = @At(value = "INVOKE", target = "Lorg/confluence/mod/common/entity/FallingStarItemEntity;summon(Lnet/minecraft/server/level/ServerLevel;)V"))
    private static void cdp$traceFallingStarTick(ServerLevel level, Operation<Void> original) {
        long startNanos = CDPPerformanceTracer.beginOtherworldPath(level);
        try {
            original.call(level);
        } finally {
            CDPPerformanceTracer.endOtherworldPath("falling star tick", startNanos, level);
        }
    }

    @WrapOperation(method = "levelTick$Post", at = @At(value = "INVOKE", target = "Lorg/confluence/mod/common/data/saved/MeteoriteTracker;tick(Lnet/minecraft/server/level/ServerLevel;)V"))
    private static void cdp$traceMeteoriteTrackerTick(MeteoriteTracker instance, ServerLevel level, Operation<Void> original) {
        long startNanos = CDPPerformanceTracer.beginOtherworldPath(level);
        try {
            original.call(instance, level);
        } finally {
            CDPPerformanceTracer.endOtherworldPath("meteorite tracker tick", startNanos, level);
        }
    }

    @WrapOperation(method = "levelTick$Post", at = @At(value = "INVOKE", target = "Lorg/confluence/mod/common/data/saved/BossDelaySpawner;tick(Lnet/minecraft/server/level/ServerLevel;)V"))
    private static void cdp$traceBossDelaySpawnerTick(BossDelaySpawner instance, ServerLevel level, Operation<Void> original) {
        long startNanos = CDPPerformanceTracer.beginOtherworldPath(level);
        try {
            original.call(instance, level);
        } finally {
            CDPPerformanceTracer.endOtherworldPath("boss delay spawner tick", startNanos, level);
        }
    }

    @WrapOperation(method = "levelTick$Post", at = @At(value = "INVOKE", target = "Lorg/confluence/mod/common/data/saved/NPCSpawner;respawnNPC(Lnet/minecraft/server/level/ServerLevel;I)V"))
    private static void cdp$traceNpcRespawnTick(ServerLevel level, int dayTime, Operation<Void> original) {
        long startNanos = CDPPerformanceTracer.beginOtherworldPath(level);
        try {
            original.call(level, dayTime);
        } finally {
            CDPPerformanceTracer.endOtherworldPath("npc respawn tick", startNanos, level);
        }
    }

    @WrapOperation(method = "levelTick$Post", at = @At(value = "INVOKE", target = "Lorg/confluence/mod/common/data/saved/HardmodeConvertor;scheduleRefill(Lnet/minecraft/server/level/ServerLevel;)V"))
    private static void cdp$traceHardmodeConvertorTick(HardmodeConvertor instance, ServerLevel level, Operation<Void> original) {
        long startNanos = CDPPerformanceTracer.beginOtherworldPath(level);
        try {
            original.call(instance, level);
        } finally {
            CDPPerformanceTracer.endOtherworldPath("hardmode convertor tick", startNanos, level);
        }
    }

    @WrapOperation(method = "serverTick$Post", at = @At(value = "INVOKE", target = "Lorg/confluence/mod/common/block/functional/network/PathService;pathFindingTick()V"))
    private static void cdp$tracePathServiceTick(PathService instance, Operation<Void> original) {
        long startNanos = CDPPerformanceTracer.beginPathServiceTick();
        try {
            original.call(instance);
        } finally {
            CDPPerformanceTracer.endPathServiceTick(startNanos);
        }
    }

    @Unique
    private static boolean cdp$shouldSkipEmptyOtherworldTick(ServerLevel level) {
        return OtherWorld.LEVEL.equals(level.dimension()) && level.players().isEmpty();
    }
}
