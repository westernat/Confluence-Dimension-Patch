package org.mesdag.confluence_dimension_patch.mixin;

import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import org.confluence.terraentity.event.GameEntityEvent;
import org.mesdag.confluence_dimension_patch.common.CDPCommonConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameEntityEvent.class, remap = false)
public abstract class GameEntityEventMixin {
    @Inject(method = "mobFinalizeSpawn", at = @At("HEAD"), cancellable = true)
    private static void cancel(FinalizeSpawnEvent event, CallbackInfo ci) {
        if (!CDPCommonConfigs.allowsConfluenceMobSpawning(event.getLevel().getLevel().dimension())) {
            ci.cancel();
        }
    }
}
