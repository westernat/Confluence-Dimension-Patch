package org.mesdag.confluence_dimension_patch.mixin;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import org.confluence.mod.Confluence;
import org.mesdag.confluence_dimension_patch.mixed.IDimensionAccessor;
import org.mesdag.confluence_dimension_patch.mixed.IStructureSet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ChunkGeneratorStructureState.class)
public abstract class ChunkGeneratorStructureStateMixin implements IDimensionAccessor {
    @Shadow
    @Final
    private List<Holder<StructureSet>> possibleStructureSets;
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

    @Inject(method = "<init>", at = @At("TAIL"))
    private void mark(RandomState randomState, BiomeSource biomeSource, long levelSeed, long cocentricRingsSeed, List<Holder<StructureSet>> possibleStructureSets, CallbackInfo ci) {
        for (Holder<StructureSet> holder : this.possibleStructureSets) {
            ResourceKey<StructureSet> key = holder.getKey();
            IStructureSet.of(holder.value()).confluence_dimension_patch$setIsFromConfluence(key != null && Confluence.MODID.equals(key.location().getNamespace()));
        }
    }
}
