package org.mesdag.confluence_dimension_patch;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.confluence.mod.common.init.ModTabs;
import org.mesdag.confluence_dimension_patch.common.TerrariumItem;

@Mod(ConfluenceDimensionPatch.MODID)
public class ConfluenceDimensionPatch {
    public static final String MODID = "confluence_dimension_patch";
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredItem<TerrariumItem> TERRARIUM = ITEMS.register("terrarium", TerrariumItem::new);

    public ConfluenceDimensionPatch(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        modEventBus.addListener(this::buildCreativeModeTabContents);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    private void buildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == ModTabs.TOOLS.getKey()) {
            event.accept(TERRARIUM);
        }
    }
}
