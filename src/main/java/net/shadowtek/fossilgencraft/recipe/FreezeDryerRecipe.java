package net.shadowtek.fossilgencraft.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record FreezeDryerRecipe (Ingredient inputItem, ItemStack output) implements Recipe<FreezeDryerRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(FreezeDryerRecipeInput pInput, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        return inputItem.test(pInput.getItem(0));

    }

    @Override
    public ItemStack assemble(FreezeDryerRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return output.copy();
    }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return output;
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FREEZEDRYER_SERIALIZER.get();
    }
    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FREEZEDRYER_TYPE.get();
    }
    public static class Serializer implements RecipeSerializer<FreezeDryerRecipe> {
        public static final MapCodec<FreezeDryerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(FreezeDryerRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(FreezeDryerRecipe::output)
        ).apply(inst, FreezeDryerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FreezeDryerRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, FreezeDryerRecipe::inputItem,
                        ItemStack.STREAM_CODEC, FreezeDryerRecipe::output,
                        FreezeDryerRecipe::new);

        @Override
        public MapCodec<FreezeDryerRecipe> codec() {
            return CODEC;
        }
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FreezeDryerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }


}
