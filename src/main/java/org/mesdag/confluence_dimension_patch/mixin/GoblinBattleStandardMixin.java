package org.mesdag.confluence_dimension_patch.mixin;

import com.xiaohunao.terra_moment.common.item.GoblinBattleStandard;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.mesdag.confluence_dimension_patch.common.OtherWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = GoblinBattleStandard.class, remap = false)
public abstract class GoblinBattleStandardMixin {
    @ModifyArg(method = "use", at = @At(value = "INVOKE", target = "Lcom/xiaohunao/heaven_destiny_moment/common/context/condition/common/LocationCondition$Builder;inDimension([Lnet/minecraft/resources/ResourceKey;)Lcom/xiaohunao/heaven_destiny_moment/common/context/condition/common/LocationCondition$Builder;"))
    private ResourceKey<Level>[] replace(ResourceKey<Level>[] dimension) {
        return new ResourceKey[]{OtherWorld.LEVEL};
    }
}
