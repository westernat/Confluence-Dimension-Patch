package org.mesdag.confluence_dimension_patch.mixin;

import net.minecraft.world.level.levelgen.SurfaceSystem;
import org.mesdag.confluence_dimension_patch.mixed.IDimensionAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SurfaceSystem.class)
public class SurfaceSystemMixin implements IDimensionAccessor {
    @Unique
    private boolean cdp$notOverworld = false;

    @Override
    public void cdp$setIsNotOverworld() {
        this.cdp$notOverworld = true;
    }

    @Override
    public boolean cdp$isNotOverworld() {
        return cdp$notOverworld;
    }
}
