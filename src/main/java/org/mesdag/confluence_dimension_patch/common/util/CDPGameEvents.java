package org.mesdag.confluence_dimension_patch.common.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;
import org.confluence.mod.util.ModUtils;
import org.mesdag.confluence_dimension_patch.ConfluenceDimensionPatch;

@EventBusSubscriber(modid = ConfluenceDimensionPatch.MODID, bus = EventBusSubscriber.Bus.GAME)
public final class CDPGameEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void mobSpawn$PositionCheck(MobSpawnEvent.PositionCheck event) {
        Mob mob = event.getEntity();
        if (mob.level().dimension() != OtherWorld.LEVEL && ModUtils.isFromConfluence(BuiltInRegistries.ENTITY_TYPE, mob.getType())) {
            event.setResult(MobSpawnEvent.PositionCheck.Result.FAIL);
        }
    }
}
