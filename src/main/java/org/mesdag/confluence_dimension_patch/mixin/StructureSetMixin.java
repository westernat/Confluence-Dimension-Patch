package org.mesdag.confluence_dimension_patch.mixin;

import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.mesdag.confluence_dimension_patch.mixed.IStructureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(StructureSet.class)
public abstract class StructureSetMixin implements IStructureSet {
    @Unique
    private boolean confluence_dimension_patch$notFromConfluence;

    @Override
    public void confluence_dimension_patch$setIsNotFromConfluence(boolean not) {
        this.confluence_dimension_patch$notFromConfluence = not;
    }

    @Override
    public boolean confluence_dimension_patch$isNotFromConfluence() {
        return confluence_dimension_patch$notFromConfluence;
    }
}
