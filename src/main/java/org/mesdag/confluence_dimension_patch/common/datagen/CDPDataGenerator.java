package org.mesdag.confluence_dimension_patch.common.datagen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.mesdag.confluence_dimension_patch.ConfluenceDimensionPatch;
import org.mesdag.confluence_dimension_patch.common.OtherWorld;

import java.util.OptionalLong;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = ConfluenceDimensionPatch.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class CDPDataGenerator {
    private static final RegistrySetBuilder DATA_BUILDER = new RegistrySetBuilder()
            .add(Registries.DIMENSION_TYPE, CDPDataGenerator::dimensionType)
            .add(Registries.LEVEL_STEM, CDPDataGenerator::levelStem);

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        boolean client = event.includeClient();
        generator.addProvider(client, new CDPItemModelProvider(output, helper));
        generator.addProvider(client, new CDPLanguageProvider(output, true));
        generator.addProvider(client, new CDPLanguageProvider(output, false));

        boolean server = event.includeServer();
        lookup = generator.addProvider(server, new DatapackBuiltinEntriesProvider(output, lookup, DATA_BUILDER, Set.of(ConfluenceDimensionPatch.MODID))).getRegistryProvider();
        generator.addProvider(server, new CDPRecipeProvider(output, lookup));
    }

    private static void dimensionType(BootstrapContext<DimensionType> context) {
        context.register(OtherWorld.DIMENSION_TYPE, new DimensionType(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                0.0F,
                new DimensionType.MonsterSettings(false, true, UniformInt.of(0, 7), 0)
        ));
    }

    private static void levelStem(BootstrapContext<LevelStem> context) {
        Holder.Reference<DimensionType> dimensionType = context.lookup(Registries.DIMENSION_TYPE).getOrThrow(OtherWorld.DIMENSION_TYPE);
        Holder.Reference<MultiNoiseBiomeSourceParameterList> multiNoiseBiomeSourceParameterList = context.lookup(Registries.MULTI_NOISE_BIOME_SOURCE_PARAMETER_LIST).getOrThrow(MultiNoiseBiomeSourceParameterLists.OVERWORLD);
        Holder.Reference<NoiseGeneratorSettings> noiseGeneratorSettings = context.lookup(Registries.NOISE_SETTINGS).getOrThrow(NoiseGeneratorSettings.OVERWORLD);
        context.register(OtherWorld.LEVEL_STEM, new LevelStem(dimensionType, new NoiseBasedChunkGenerator(MultiNoiseBiomeSource.createFromPreset(multiNoiseBiomeSourceParameterList), noiseGeneratorSettings)));
    }
}
