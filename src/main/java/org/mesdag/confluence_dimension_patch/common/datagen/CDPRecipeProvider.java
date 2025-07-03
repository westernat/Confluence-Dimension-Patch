package org.mesdag.confluence_dimension_patch.common.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import org.mesdag.confluence_dimension_patch.ConfluenceDimensionPatch;

import java.util.concurrent.CompletableFuture;

public class CDPRecipeProvider extends RecipeProvider {
    public CDPRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider holderLookup) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ConfluenceDimensionPatch.TERRARIUM)
                .define('A', Ingredient.of(Tags.Items.GLASS_PANES))
                .define('B', Ingredient.of(ItemTags.SAPLINGS))
                .define('C', Ingredient.of(ItemTags.PLANKS))
                .pattern(" A ")
                .pattern("ABA")
                .pattern("CCC")
                .unlockedBy("has_sapling", has(ItemTags.SAPLINGS))
                .save(recipeOutput);
    }
}
