package org.mesdag.confluence_dimension_patch.client.mixin;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import org.confluence.mod.client.gameevent.ClientGameEventSystem;
import org.mesdag.confluence_dimension_patch.common.OtherWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientGameEventSystem.class, remap = false)
public abstract class ClientGameEventSystemMixin {
    @Inject(method = "afterRenderSky", at = @At("HEAD"), cancellable = true)
    private static void cdp$skipAfterRenderSkyOutsideOtherworld(RenderLevelStageEvent event, LocalPlayer player, CallbackInfo ci) {
        if (player.level().dimension() != OtherWorld.LEVEL) {
            ci.cancel();
        }
    }

    @Inject(method = "handleBloodMoon", at = @At("HEAD"), cancellable = true)
    private static void cdp$skipBloodMoonOutsideOtherworld(Player player, boolean started, CallbackInfo ci) {
        if (cdp$isOutsideOtherworld(player)) {
            ClientGameEventSystem.moonTexture = null;
            ClientGameEventSystem.lightTextureColor = null;
            ci.cancel();
        }
    }

    @Inject(method = "handleSpecificMoon", at = @At("HEAD"), cancellable = true)
    private static void cdp$skipSpecificMoonOutsideOtherworld(Player player, boolean started, CallbackInfo ci) {
        if (cdp$isOutsideOtherworld(player)) {
            ClientGameEventSystem.moonTexture = null;
            ci.cancel();
        }
    }

    private static boolean cdp$isOutsideOtherworld(Player player) {
        return player.level().dimension() != OtherWorld.LEVEL;
    }
}