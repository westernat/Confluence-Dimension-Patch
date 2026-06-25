package org.mesdag.confluence_dimension_patch.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.mesdag.confluence_dimension_patch.common.CDPCommonConfigs;
import org.mesdag.confluence_dimension_patch.mixed.IDimensionAccessor;
import org.mesdag.confluence_dimension_patch.mixed.IStructureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {
    @ModifyExpressionValue(method = "lambda$createStructures$14", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement;isStructureChunk(Lnet/minecraft/world/level/chunk/ChunkGeneratorStructureState;II)Z"))
    private boolean skip(boolean original, @Local(argsOnly = true) ChunkGeneratorStructureState structureState, @Local(argsOnly = true) Holder<StructureSet> holder) {
        ResourceKey<Level> dimension = IDimensionAccessor.of(structureState).cdp$getDimension();
        return original && (CDPCommonConfigs.allowsConfluenceStructureGeneration(dimension)
                || IStructureSet.of(holder.value()).cdp$isNotFromConfluence());
    }
}
