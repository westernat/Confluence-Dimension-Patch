package org.mesdag.confluence_dimension_patch.mixed;

import net.minecraft.world.level.levelgen.structure.StructureSet;

public interface IStructureSet {
    void confluence_dimension_patch$setIsNotFromConfluence(boolean not);

    boolean confluence_dimension_patch$isNotFromConfluence();

    static IStructureSet of(StructureSet value) {
        return (IStructureSet) (Record) value;
    }
}
