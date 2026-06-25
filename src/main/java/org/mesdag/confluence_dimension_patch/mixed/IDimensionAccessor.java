package org.mesdag.confluence_dimension_patch.mixed;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.mesdag.confluence_dimension_patch.common.OtherWorld;

public interface IDimensionAccessor {
    void cdp$setDimension(ResourceKey<Level> dimension);

    ResourceKey<Level> cdp$getDimension();

    default boolean cdp$isOverworld() {
        return Level.OVERWORLD.equals(cdp$getDimension());
    }

        default boolean cdp$isNotOverworld() {
        return !cdp$isOverworld();
    }

    default boolean cdp$isOtherworld() {
        return OtherWorld.LEVEL.equals(cdp$getDimension());
    }

    default boolean cdp$isNotOtherworld() {
        return !cdp$isOtherworld();
    }

    static IDimensionAccessor of(Object o) {
        return (IDimensionAccessor) o;
    }
}
