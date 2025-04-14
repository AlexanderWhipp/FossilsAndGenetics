package net.shadowtek.fossilgencraft.datagen;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.block.ModBlocks;
import net.shadowtek.fossilgencraft.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.shadowtek.fossilgencraft.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TEST_TUBE_EMPTY.get(), 16)
                .pattern("A A")
                .pattern("A A")
                .pattern("AAA")
                .define('A', Items.GLASS_PANE)
                .unlockedBy(getHasName(Items.GLASS_PANE), has(Items.GLASS_PANE)).save(pRecipeOutput);


//syringes
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.SYRINGE_CONTAMINATED.get()),
                        RecipeCategory.MISC,
                        ModItems.SYRINGE_EMPTY.get(),
                        0.2f,
                        100)
                .unlockedBy("has_syringe_contaminated", has(ModItems.SYRINGE_CONTAMINATED.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID,"syringe_blast_cleaning_recipe_custom"));

//test tubes
    /*    ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TEST_TUBE_BLOOD_CHICKEN.get())
                .requires(ModItems.SYRINGE_FILLED_BLOOD.get())
                .requires(ModItems.TEST_TUBE_EMPTY.get())
                .unlockedBy("has_syringe_blood_chicken", has(ModItems.SYRINGE_BLOOD_CHICKEN.get()))
                .save(pRecipeOutput,ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "chicken_dna_sample_recipe"));
*/
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TEST_TUBE_BLOOD_PIG.get())
                .requires(ModItems.SYRINGE_BLOOD_PIG.get())
                .requires(ModItems.TEST_TUBE_EMPTY.get())
                .unlockedBy("has_syringe_blood_pig", has(ModItems.SYRINGE_BLOOD_PIG.get()))
                .save(pRecipeOutput,ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "pig_dna_sample_recipe"));
//Chemical Solutions
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CHEMICAL_LYSIS_BUFFER.get())
                .requires(ModItems.DETERGENT.get())
                .requires(ModItems.ENZYME_POWDER.get())
                .requires(Items.REDSTONE)
                .unlockedBy("has_enzyme_powder", has(ModItems.ENZYME_POWDER.get()))
                .save(pRecipeOutput,ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "chemical_lysis_recipe"));

//Enzyme ingredients
        Ingredient anyMushroomIngredient = Ingredient.of(Items.BROWN_MUSHROOM, Items.RED_MUSHROOM);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FUNGAL_ENZYME_SAMPLE.get())
                .requires(ModItems.TEST_TUBE_WATER.get())
                .requires(anyMushroomIngredient)
                .unlockedBy("has_test_tube_water", has(ModItems.TEST_TUBE_WATER.get()))
                .save(pRecipeOutput,ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "fungal_enzyme_sample_recipe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MORTAR_AND_PESTLE.get())
                .requires(Items.FLINT)
                .requires(Items.BOWL)
                .unlockedBy("has_flint", has(Items.FLINT))
                .save(pRecipeOutput,ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "mortar_and_pestle_recipe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENZYME_POWDER.get())
                .requires(ModItems.MORTAR_AND_PESTLE.get())
                .requires(ModItems.ENZYME_RESIDUE_DRIED.get())
                .unlockedBy("has_enzyme_residue_dried", has(ModItems.ENZYME_RESIDUE_DRIED.get()))
                .save(pRecipeOutput,ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "enzyme_powder_recipe"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ENZYME_RESIDUE.get())
                .requires(Items.SLIME_BALL)
                .requires(ModItems.FUNGAL_ENZYME_SAMPLE.get())
                .unlockedBy("has_fungal_enzyme_sample", has(ModItems.FUNGAL_ENZYME_SAMPLE.get()))
                .save(pRecipeOutput,ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "enzyme_residue_recipe"));


//Detergent Ingredients


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.DETERGENT.get())
                .requires(ModItems.SULFUR.get())
                .requires(ModItems.PIG_FAT.get())
                .requires(ModItems.ASH.get())
                .unlockedBy("has_sulfur", has(ModItems.SULFUR.get()))
                .save(pRecipeOutput,ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "detergent_recipe"));




        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(ModTags.Items.SMELTABLE_TO_ASH),
                        RecipeCategory.MISC,
                        ModItems.ASH.get(),
                        0.2f,
                        100)
                .unlockedBy("has_syringe_contaminated", has(ModItems.SYRINGE_CONTAMINATED.get()))
                .save(pRecipeOutput, ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID,"ash_smelting_recipe"));






    }



}

