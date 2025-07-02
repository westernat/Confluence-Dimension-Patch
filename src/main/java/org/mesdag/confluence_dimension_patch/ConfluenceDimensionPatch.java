package org.mesdag.confluence_dimension_patch;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ConfluenceDimensionPatch.MODID)
public class ConfluenceDimensionPatch {
    public static final String MODID = "confluence_dimension_patch";
    public static final Logger LOGGER = LoggerFactory.getLogger("ConfluenceDimensionPatch");
    public static final ResourceKey<Level> OTHERWORLD = ResourceKey.create(Registries.DIMENSION, asResource("otherworld"));

    public ConfluenceDimensionPatch(IEventBus modEventBus, ModContainer modContainer) {}

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
