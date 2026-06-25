package org.mesdag.confluence_dimension_patch.mixin.integration.ageratum;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import org.confluence.mod.integration.ageratum.AgeratumHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = AgeratumHelper.class, remap = false)
public abstract class AgeratumHelperMixin {
    @Inject(method = "register", at = @At("HEAD"), cancellable = true)
    private static void cdp$skipDedicatedServer(IEventBus eventBus, CallbackInfo ci) {
        if (FMLEnvironment.dist != Dist.CLIENT) {
            ci.cancel();
        }
    }
}
