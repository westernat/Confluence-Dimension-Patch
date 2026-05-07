package org.mesdag.confluence_dimension_patch.mixin;

import com.google.common.collect.Iterables;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.SurfaceSystem;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import org.confluence.mod.Confluence;
import org.mesdag.confluence_dimension_patch.mixed.IDimensionAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseBasedChunkGeneratorMixin extends ChunkGenerator {
    public NoiseBasedChunkGeneratorMixin(BiomeSource biomeSource) {
        super(biomeSource);
    }

    @ModifyExpressionValue(method = "applyCarvers", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/BiomeGenerationSettings;getCarvers(Lnet/minecraft/world/level/levelgen/GenerationStep$Carving;)Ljava/lang/Iterable;"))
    private Iterable<Holder<ConfiguredWorldCarver<?>>> filter(Iterable<Holder<ConfiguredWorldCarver<?>>> original) {
        if (IDimensionAccessor.of(getBiomeSource()).cdp$isOverworld()) {
            return Iterables.filter(original, holder -> holder.getKey() != null && !Confluence.MODID.equals(holder.getKey().location().getNamespace()));
        }
        return original;
    }

    @ModifyExpressionValue(method = "buildSurface(Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/world/level/levelgen/WorldGenerationContext;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/biome/BiomeManager;Lnet/minecraft/core/Registry;Lnet/minecraft/world/level/levelgen/blending/Blender;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/RandomState;surfaceSystem()Lnet/minecraft/world/level/levelgen/SurfaceSystem;"))
    private SurfaceSystem apply(SurfaceSystem original) {
        if (IDimensionAccessor.of(getBiomeSource()).cdp$isNotOverworld()) {
            IDimensionAccessor.of(original).cdp$setIsNotOverworld();
        }
        return original;
    }
}
