package net.shadowtek.fossilgencraft.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.shadowtek.fossilgencraft.event.ModDataComponents;
import org.jetbrains.annotations.NotNull;

public record DnaSplicerRecipe(Ingredient inputItem1, Ingredient inputItem2, ItemStack output1) implements Recipe<DnaSplicerRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem1);
        list.add(inputItem2);
        return list;
    }
    @Override
    public boolean matches(CraftingContainer container, Level level) {
        ItemStack stack1 = container.getItem(0);
        ItemStack stack2 = container.getItem(1);

        if (stack1.isEmpty() || stack2.isEmpty()) return false;

        ResourceLocation id1 = stack1.get(ModDataComponents.DNA_SPECIES_ID);
        @NotNull DataComponentType<String> id2 = ModDataComponents.DNA_SPECIES_ID.get();
    }

    @Override
    public ItemStack assemble(DnaSplicerRecipeInput pInput, HolderLookup.Provider pRegistries) {
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
        return ModRecipes.DNA_SPLICER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.DNA_SPLICER_TYPE.get();
    }
    public static class Serializer implements RecipeSerializer<DnaSplicerRecipe> {
        public static final MapCodec<DnaSplicerRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient1").forGetter(DnaSplicerRecipe::inputItem1),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient2").forGetter(DnaSplicerRecipe::inputItem2),
                ItemStack.CODEC.fieldOf("result1").forGetter(DnaSplicerRecipe::output1)
        ).apply(inst, DnaSplicerRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, DnaSplicerRecipe> STREAM_CODEC =
                StreamCodec.of(
                        (buf,recipe) -> {
                            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem1());
                            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem2());
                            ItemStack.STREAM_CODEC.encode(buf, recipe.output1());

                        },
                        (buf) -> {
                            Ingredient ing1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                            Ingredient ing2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);

                            ItemStack out1 = ItemStack.STREAM_CODEC.decode(buf);

                            return new DnaSplicerRecipe(ing1,ing2,out1);
                        }
                );

        @Override
        public MapCodec<DnaSplicerRecipe> codec() {
            return CODEC;
        }
        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DnaSplicerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
