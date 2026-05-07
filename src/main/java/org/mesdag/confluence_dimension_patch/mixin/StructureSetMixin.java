package org.mesdag.confluence_dimension_patch.mixin;

import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.mesdag.confluence_dimension_patch.mixed.IStructureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(StructureSet.class)
public abstract class StructureSetMixin implements IStructureSet {
    @Unique
    private boolean cdp$notFromConfluence;

    @Override
    public void cdp$setIsNotFromConfluence(boolean not) {
        this.cdp$notFromConfluence = not;
    }

    @Override
    public boolean cdp$isNotFromConfluence() {
        return cdp$notFromConfluence;
    }
}
