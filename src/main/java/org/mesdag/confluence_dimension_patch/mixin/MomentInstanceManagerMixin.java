package org.mesdag.confluence_dimension_patch.mixin;

import com.xiaohunao.heaven_destiny_moment.common.moment.MomentInstance;
import com.xiaohunao.heaven_destiny_moment.common.moment.MomentInstanceBuilder;
import com.xiaohunao.heaven_destiny_moment.common.moment.MomentInstanceManager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MomentInstanceManager.class, remap = false)
public abstract class MomentInstanceManagerMixin {
    @Inject(method = "createMomentInstance", at = @At("HEAD"), cancellable = true)
    private static void skip(MomentInstanceBuilder builder, CallbackInfoReturnable<MomentInstance> cir) {
        if (cir != null && builder.getLevel().dimension() == Level.OVERWORLD) {
            cir.setReturnValue(null);
        }
    }
}
