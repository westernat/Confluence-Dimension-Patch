package org.mesdag.confluence_dimension_patch.common;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundLevelEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import org.confluence.lib.ConfluenceMagicLib;
import org.confluence.lib.common.component.ModRarity;
import org.confluence.lib.util.LibUtils;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TerrariumItem extends Item {
    public TerrariumItem() {
        super(new Properties().stacksTo(1).fireResistant().component(ConfluenceMagicLib.MOD_RARITY, ModRarity.EXPERT));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        return ItemUtils.startUsingInstantly(level, player, usedHand);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 40;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.getVehicle() != null) {
                serverPlayer.removeVehicle();
            }
            serverPlayer.getCooldowns().addCooldown(this, 20);
            boolean toOtherworld = level.dimension() == Level.OVERWORLD;
            ServerLevel serverlevel = serverPlayer.server.getLevel(toOtherworld ? OtherWorld.LEVEL : Level.OVERWORLD);
            if (serverlevel != null) {
                serverPlayer.changeDimension(new DimensionTransition(
                        serverlevel,
                        serverPlayer.position().add(0, 0.1, 0),
                        serverPlayer.getDeltaMovement(),
                        serverPlayer.getYRot(),
                        serverPlayer.getXRot(),
                        entity -> {
                            if (entity instanceof ServerPlayer serverplayer) {
                                serverplayer.connection.send(new ClientboundLevelEventPacket(1032, BlockPos.ZERO, 0, false));
                                LibUtils.updateItemStackNbt(stack, tag -> tag.putInt("dimension", toOtherworld ? 1 : 0));
                            }
                        }
                ));
            }
        }
        return stack;
    }
}
