package org.mesdag.confluence_dimension_patch.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.confluence.mod.common.data.saved.HardmodeConvertor;
import org.mesdag.confluence_dimension_patch.common.util.OtherWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = HardmodeConvertor.class, remap = false)
public abstract class HardmodeConvertorMixin {
    @WrapOperation(method = "lambda$start$1", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;overworld()Lnet/minecraft/server/level/ServerLevel;"))
    private static ServerLevel replace(MinecraftServer instance, Operation<ServerLevel> original) {
        return instance.getLevel(OtherWorld.LEVEL);
    }
}
