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
import net.minecraftforge.fml.common.Mod;

public record DnaSplittingCentrifuge(Ingredient inputItem1, Ingredient inputItem2, ItemStack gene1, ItemStack gene2, ItemStack gene3, ItemStack gene4, ItemStack gene5, ItemStack gene6, ItemStack gene7, ItemStack gene8, ItemStack gene9, ItemStack gene10) implements Recipe<CentrifugeRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients(){
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem1);
        list.add(inputItem2);
        return list;
    }



    @Override
    public boolean matches(CentrifugeRecipeInput pInput, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        if (pInput.size() != 2) {
           return false;
        }
        if(!inputItem1.test(pInput.getItem(0))) return false;
        if(!inputItem2.test(pInput.getItem(1))) return false;

        return true;
    }

    @Override
    public ItemStack assemble(CentrifugeRecipeInput pInput, HolderLookup.Provider pRegistries) {
        return gene1.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return gene1.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.CENTRIFUGE_SPLITTING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.CENTRIFUGE_SPLITTING_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<DnaSplittingCentrifuge> {
        public static final MapCodec<DnaSplittingCentrifuge> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient1").forGetter(DnaSplittingCentrifuge::inputItem1),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient2").forGetter(DnaSplittingCentrifuge::inputItem2),
                ItemStack.CODEC.fieldOf("gene1").forGetter(DnaSplittingCentrifuge::gene1),
                ItemStack.CODEC.fieldOf("gene2").forGetter(DnaSplittingCentrifuge::gene2),
                ItemStack.CODEC.fieldOf("gene3").forGetter(DnaSplittingCentrifuge::gene3),
                ItemStack.CODEC.fieldOf("gene4").forGetter(DnaSplittingCentrifuge::gene4),
                ItemStack.CODEC.fieldOf("gene5").forGetter(DnaSplittingCentrifuge::gene5),
                ItemStack.CODEC.fieldOf("gene6").forGetter(DnaSplittingCentrifuge::gene6),
                ItemStack.CODEC.fieldOf("gene7").forGetter(DnaSplittingCentrifuge::gene7),
                ItemStack.CODEC.fieldOf("gene8").forGetter(DnaSplittingCentrifuge::gene8),
                ItemStack.CODEC.fieldOf("gene9").forGetter(DnaSplittingCentrifuge::gene9),
                ItemStack.CODEC.fieldOf("gene10").forGetter(DnaSplittingCentrifuge::gene10)
        ).apply(inst, DnaSplittingCentrifuge::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, DnaSplittingCentrifuge> STREAM_CODEC =
                StreamCodec.of((buf, recipe) -> {
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem1());
                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.inputItem2());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.gene1());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.gene2());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.gene3());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.gene4());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.gene5());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.gene6());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.gene7());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.gene8());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.gene9());
                    ItemStack.STREAM_CODEC.encode(buf, recipe.gene10());
                },
                        (buf) -> {
                            Ingredient ing1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                            Ingredient ing2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);

                            ItemStack out1 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out2 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out3 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out4 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out5 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out6 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out7 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out8 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out9 = ItemStack.STREAM_CODEC.decode(buf);
                            ItemStack out10 = ItemStack.STREAM_CODEC.decode(buf);
                            return new DnaSplittingCentrifuge(ing1,ing2,out1,out2,out3,out4,out5,out6,out7,out8,out9,out10);
                        }
                        );




        @Override
        public MapCodec<DnaSplittingCentrifuge> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, DnaSplittingCentrifuge> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
