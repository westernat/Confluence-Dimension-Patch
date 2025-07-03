package org.mesdag.confluence_dimension_patch.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.confluence.mod.common.init.ModBiomes;
import org.mesdag.confluence_dimension_patch.common.OnlyInOtherworldRuleSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ModBiomes.class)
public abstract class ModBiomesMixin {
    @ModifyExpressionValue(method = "registerRegionAndSurface", at = @At(value = "INVOKE", target = "Lorg/confluence/mod/common/worldgen/biome/SurfaceRuleData;makeMinecraftOverWorldRules()Lnet/minecraft/world/level/levelgen/SurfaceRules$RuleSource;"))
    private static SurfaceRules.RuleSource proxy(SurfaceRules.RuleSource original) {
        return new OnlyInOtherworldRuleSource(original);
    }
}
