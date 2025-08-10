package org.mesdag.confluence_dimension_patch.mixin;

import com.xiaohunao.terra_moment.common.item.BloodyTearItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.mesdag.confluence_dimension_patch.common.OtherWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BloodyTearItem.class)
public class BloodyTearItemMixin {

    @ModifyArg(method = "use", at = @At(value = "INVOKE", target = "Lcom/xiaohunao/heaven_destiny_moment/common/context/condition/common/LocationCondition$Builder;inDimension([Lnet/minecraft/resources/ResourceKey;)Lcom/xiaohunao/heaven_destiny_moment/common/context/condition/common/LocationCondition$Builder;"))
    private ResourceKey<Level>[] replace(ResourceKey<Level>[] dimension) {
        return new ResourceKey[]{OtherWorld.LEVEL};
    }

}
