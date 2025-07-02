package org.mesdag.confluence_dimension_patch.mixin;

import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import org.mesdag.confluence_dimension_patch.mixed.IDimensionAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChunkGeneratorStructureState.class)
public abstract class ChunkGeneratorStructureStateMixin implements IDimensionAccessor {
    @Unique
    private boolean confluence_dimension_patch$allows = false;

    @Override
    public void confluence_dimension_patch$setAllows() {
        this.confluence_dimension_patch$allows = true;
    }

    @Override
    public boolean confluence_dimension_patch$allows() {
        return confluence_dimension_patch$allows;
    }
}
