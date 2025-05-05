package net.shadowtek.fossilgencraft.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

public record CentrifugeRecipe(Ingredient inputItem1,Ingredient inputItem2, ItemStack output1, ItemStack output2) implements Recipe<CentrifugeRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem1);
        list.add(inputItem2);
        return list;
    }

    @Override
    public boolean matches(CentrifugeRecipeInput pInput, Level pLevel) {
        if (pLevel.isClientSide()) {
            return false;
        }
        if (pInput.size() != 2) {
            System.out.println("Wrong number of inputs for Centrifuge recipe!");
            return false;
        }
        if (!inputItem1.test(pInput.getItem(0))) return false;
        if (!inputItem2.test(pInput.getItem(1))) return false;
        System.out.println("Centrifuge recipe matches!");

        return true;

    }

    @Override
    public ItemStack assemble(CentrifugeRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return output1.copy();
    }
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return output1.copy();
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CENTRIFUGE_SERIALIZER.get();
    }
    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CENTRIFUGE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<CentrifugeRecipe> {
        public static final MapCodec<CentrifugeRecipe> CODEC = RecordCodecBuilder.mapCodec(inst-> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient1").forGetter(CentrifugeRecipe::inputItem1),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient2").forGetter(CentrifugeRecipe::inputItem2),
                ItemStack.CODEC.fieldOf("result1").forGetter(CentrifugeRecipe::output1),
                ItemStack.CODEC.fieldOf("result2").forGetter(CentrifugeRecipe::output2)
        ).apply(inst, CentrifugeRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CentrifugeRecipe> STREAM_CODEC =
                StreamCodec.of(
                        (buf, recipe) -> {
                        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem1());
                        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem2());
                        ItemStack.STREAM_CODEC.encode(buf, recipe.output1());
                        ItemStack.STREAM_CODEC.encode(buf, recipe.output2());
                        },
                        (buf) -> {
                            Ingredient ing1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                            Ingredient ing2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);

                            ItemStack out1 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out2 = ItemStack.STREAM_CODEC.decode(buf);
                            return new CentrifugeRecipe(ing1,ing2,out1,out2);
                        }
                        );

        @Override
        public MapCodec<CentrifugeRecipe> codec() {
            return CODEC;
        }
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CentrifugeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }



}