package org.mesdag.confluence_dimension_patch.mixin;

import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import org.confluence.mod.common.event.game.entity.LivingEntityEvents;
import org.mesdag.confluence_dimension_patch.common.CDPCommonConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntityEvents.class, remap = false)
public abstract class LivingEntityEventsMixin {
    @Inject(method = "finalizeSpawn", at = @At("HEAD"), cancellable = true)
    private static void cancel(FinalizeSpawnEvent event, CallbackInfo ci) {
        if (!CDPCommonConfigs.allowsConfluenceMobSpawning(event.getLevel().getLevel().dimension())) {
            ci.cancel();
        }
    }
}
