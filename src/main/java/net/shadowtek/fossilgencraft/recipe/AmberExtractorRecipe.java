package net.shadowtek.fossilgencraft.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sun.jna.platform.unix.solaris.LibKstat;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;

public record AmberExtractorRecipe(Ingredient inputItem_1, Ingredient inputItem_2, Ingredient inputItem_3, Ingredient inputItem_4,
                                   ItemStack output_1, ItemStack output_2, ItemStack output_3, ItemStack output_4) implements Recipe<AmberExtractorRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem_1);
        list.add(inputItem_2);
        list.add(inputItem_3);
        list.add(inputItem_4);
        return list;
    }

    @Override
    public boolean matches(AmberExtractorRecipeInput pInput, Level pLevel) {
        if (pLevel.isClientSide()) {
            // Recipe matching should typically happen server-side
            return false;
        }
        // Check if the number of input slots matches expected (optional but good practice)
        if (pInput.size() != 4) {
            return false;
        }
        // Check each ingredient against the corresponding item in the input wrapper
        if (!inputItem_1.test(pInput.getItem(0))) return false; // Check input 1 against slot 0
        if (!inputItem_2.test(pInput.getItem(1))) return false; // Check input 2 against slot 1
        if (!inputItem_3.test(pInput.getItem(2))) return false; // Check input 3 against slot 2
        if (!inputItem_4.test(pInput.getItem(3))) return false; // Check input 4 against slot 3

        // If all checks passed:
        return true;

    }

    @Override
    public ItemStack assemble(AmberExtractorRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return output_1.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return output_1.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.AMBER_EXTRACTOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.AMBER_EXTRACTOR_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<AmberExtractorRecipe> {

        public static final MapCodec<AmberExtractorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient1").forGetter(AmberExtractorRecipe::inputItem_1),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient2").forGetter(AmberExtractorRecipe::inputItem_2),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient3").forGetter(AmberExtractorRecipe::inputItem_3),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient4").forGetter(AmberExtractorRecipe::inputItem_4),
                ItemStack.CODEC.fieldOf("result1").forGetter(AmberExtractorRecipe::output_1), // Use unique names like result1, result2...
                ItemStack.CODEC.fieldOf("result2").forGetter(AmberExtractorRecipe::output_2),
                ItemStack.CODEC.fieldOf("result3").forGetter(AmberExtractorRecipe::output_3),
                ItemStack.CODEC.fieldOf("result4").forGetter(AmberExtractorRecipe::output_4)
        ).apply(inst, AmberExtractorRecipe::new)); // Calls the record constructor with all 8 fields in order

        public static final StreamCodec<RegistryFriendlyByteBuf, AmberExtractorRecipe> STREAM_CODEC =
                StreamCodec.of(
                        // Encoder lambda: (buffer, recipe) -> { write fields }
                        (buf, recipe) -> {
                            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem_1());
                            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem_2());
                            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem_3());
                            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem_4());
                            ItemStack.STREAM_CODEC.encode(buf, recipe.output_1());
                            ItemStack.STREAM_CODEC.encode(buf, recipe.output_2());
                            ItemStack.STREAM_CODEC.encode(buf, recipe.output_3());
                            ItemStack.STREAM_CODEC.encode(buf, recipe.output_4());
                        },
                        // Decoder lambda: (buffer) -> { read fields; return new Recipe(...) }
                        (buf) -> {
                            Ingredient ing1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                            Ingredient ing2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                            Ingredient ing3 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                            Ingredient ing4 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                            ItemStack out1 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out2 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out3 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out4 = ItemStack.STREAM_CODEC.decode(buf);
                            return new AmberExtractorRecipe(ing1, ing2, ing3, ing4, out1, out2, out3, out4);
                        }
                );


        @Override
        public MapCodec<AmberExtractorRecipe> codec () {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AmberExtractorRecipe> streamCodec () {
            return STREAM_CODEC;
        }

    }
}
