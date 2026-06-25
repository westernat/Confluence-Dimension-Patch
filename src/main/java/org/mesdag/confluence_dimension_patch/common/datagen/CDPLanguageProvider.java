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
        add("tooltip.item.confluence.terrarium.0", "Minecraft", "我的世界");
        add("tooltip.item.confluence.terrarium.1", "Confluence: Otherworld", "汇流来世");
        add("tooltip.item.confluence.terrarium.2", "Currently In", "目前位于");
        add("message.confluence_dimension_patch.unavailable", "You can't use it in current dimension!", "你不能在当前维度使用它！");
        add("confluence_dimension_patch.configuration.initialTerrarium", "Initial Terrarium", "初始盒中泰拉");
        add("confluence_dimension_patch.configuration.initialTerrarium.tooltip", "After opening, the opening will give the player a Terrarium by default", "开启后开局将默认给予玩家一个盒中泰拉");
        add("confluence_dimension_patch.configuration.allowNetherMobSpawning", "Allow Nether Mob Spawning", "允许下界怪物生成");
        add("confluence_dimension_patch.configuration.allowNetherMobSpawning.tooltip", "Allows Confluence and TerraEntity mobs to spawn in the Nether", "允许 Confluence 和 TerraEntity 的怪物在下界生成");
        add("confluence_dimension_patch.configuration.allowEndMobSpawning", "Allow End Mob Spawning", "允许末地怪物生成");
        add("confluence_dimension_patch.configuration.allowEndMobSpawning.tooltip", "Allows Confluence and TerraEntity mobs to spawn in the End", "允许 Confluence 和 TerraEntity 的怪物在末地生成");
        add("confluence_dimension_patch.configuration.allowNetherBiomeGeneration", "Allow Nether Biome Generation", "允许下界群系生成");
        add("confluence_dimension_patch.configuration.allowNetherBiomeGeneration.tooltip", "Allows Confluence Nether biomes and terrain features to generate in the vanilla Nether", "允许 Confluence 的下界群系和地形特征在原版下界生成");
        add("confluence_dimension_patch.configuration.allowEndBiomeGeneration", "Allow End Biome Generation", "允许末地群系生成");
        add("confluence_dimension_patch.configuration.allowEndBiomeGeneration.tooltip", "Allows Confluence End biomes and terrain features to generate in the vanilla End", "允许 Confluence 的末地群系和地形特征在原版末地生成");
        add("confluence_dimension_patch.configuration.allowNetherStructureGeneration", "Allow Nether Structure Generation", "允许下界结构生成");
        add("confluence_dimension_patch.configuration.allowNetherStructureGeneration.tooltip", "Allows Confluence structures to generate in the vanilla Nether", "允许 Confluence 的结构在原版下界生成");
        add("confluence_dimension_patch.configuration.allowEndStructureGeneration", "Allow End Structure Generation", "允许末地结构生成");
        add("confluence_dimension_patch.configuration.allowEndStructureGeneration.tooltip", "Allows Confluence structures to generate in the End", "允许 Confluence 的结构在末地生成");
        add("confluence_dimension_patch.configuration.title", "Confluence Dimension Patch", "汇流来世维度补丁");
    }

    public void add(Item key, String en, String zh) {
        super.add(key, isEn ? en : zh);
    }

    public void add(String key, String en, String zh) {
        super.add(key, isEn ? en : zh);
    }
}
