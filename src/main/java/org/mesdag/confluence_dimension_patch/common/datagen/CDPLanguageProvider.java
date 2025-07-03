package org.mesdag.confluence_dimension_patch.common.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.mesdag.confluence_dimension_patch.ConfluenceDimensionPatch;

public class CDPLanguageProvider extends LanguageProvider {
    private final boolean isEn;

    public CDPLanguageProvider(PackOutput output, boolean isEn) {
        super(output, ConfluenceDimensionPatch.MODID, isEn ? "en_us" : "zh_cn");
        this.isEn = isEn;
    }

    @Override
    protected void addTranslations() {
        add(ConfluenceDimensionPatch.TERRARIUM.get(), "Terrarium", "盒中泰拉");
    }

    public void add(Item key, String en, String zh) {
        super.add(key, isEn ? en : zh);
    }
}
