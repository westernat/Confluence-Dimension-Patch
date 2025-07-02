package org.mesdag.confluence_dimension_patch.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.mesdag.confluence_dimension_patch.mixed.IDimensionAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {
    @WrapOperation(method = "lambda$createStructures$14", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement;isStructureChunk(Lnet/minecraft/world/level/chunk/ChunkGeneratorStructureState;II)Z"))
    private boolean skip(StructurePlacement instance, ChunkGeneratorStructureState structureState, int x, int z, Operation<Boolean> original) {
        if (((IDimensionAccessor) structureState).confluence_dimension_patch$allows()) {
            return original.call(instance, structureState, x, z);
        }
        return false;
    }
}
