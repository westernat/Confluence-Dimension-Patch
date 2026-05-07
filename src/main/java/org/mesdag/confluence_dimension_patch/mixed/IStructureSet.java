package org.mesdag.confluence_dimension_patch.mixed;

import net.minecraft.world.level.levelgen.structure.StructureSet;

public interface IStructureSet {
    void cdp$setIsNotFromConfluence(boolean not);

    boolean cdp$isNotFromConfluence();

    static IStructureSet of(StructureSet value) {
        return (IStructureSet) (Record) value;
    }
}
