package org.mesdag.confluence_dimension_patch.common.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.mesdag.confluence_dimension_patch.ConfluenceDimensionPatch;

public class CDPItemModelProvider extends ItemModelProvider {
    public CDPItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ConfluenceDimensionPatch.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ResourceLocation otherworld = ConfluenceDimensionPatch.asResource("item/terrarium_otherworld");
        withExistingParent("terrarium_otherworld", "item/generated")
                .texture("layer0", otherworld);
        withExistingParent("terrarium", "item/generated")
                .texture("layer0", ConfluenceDimensionPatch.asResource("item/terrarium_overworld"))
                .override()
                .predicate(ConfluenceDimensionPatch.asResource("dimension"), 1)
                .model(new ModelFile.UncheckedModelFile(otherworld))
                .end();
    }
}
