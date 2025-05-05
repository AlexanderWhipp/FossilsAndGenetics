package net.shadowtek.fossilgencraft.recipe.splicing.dinosaurs;

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
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.recipe.DnaSequencerRecipe;
import net.shadowtek.fossilgencraft.recipe.ModRecipes;

public record DinosaurSplicingRecipe(Ingredient growthGene, Ingredient metabolismGene, Ingredient skeletalGene, Ingredient muscleGene,
                                     Ingredient skinGene, Ingredient respiratoryGene, Ingredient nervousSystemGene, Ingredient sensoryGene,
                                     Ingredient immunityGene, Ingredient reproductiveGene, Ingredient reagent, Ingredient buffer,
                                     ItemStack completedGenome) implements Recipe<DinosaurSplicingRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        System.err.println("Task1 Init: Getting Gene Splicer Ingrediants...");
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(growthGene);list.add(metabolismGene);list.add(skeletalGene);list.add(muscleGene);list.add(skinGene);
        list.add(respiratoryGene);list.add(nervousSystemGene);list.add(sensoryGene);list.add(immunityGene);
        list.add(reproductiveGene);list.add(reagent);list.add(buffer);
        System.err.println("Task1 Com: Successfully retrieved" + list + "Total Genes Detected:" +(long) list.size());
        return list;
                                                     }//All FOSSILGENCRAFT CUSTOM RECIPES TO FOLLOW THIS LAYOUT FORMAT, HELPS KEEP READABILITY
                                                        //Escpecially for recipes like this one which has not yet been optimised to loop its inputs
                                                 @Override
                                                 public boolean matches(DinosaurSplicingRecipeInput pInput, Level pLevel) {
                                                 if(pLevel.isClientSide()) {
                                                     return false;
                                                 }
                                                     if (pInput.size() != 12) {
                                                         FossilGenCraft.LOGGER.warn("Recipe Match Failed: Input size was {}, expected 15", pInput.size());
                                                         return false;
                                                     }




                                                     boolean match;


                                                     ItemStack item0 = pInput.getItem(0);
                                                     match = growthGene.test(item0);
                                                     if (!match) return false;


                                                     ItemStack item1 = pInput.getItem(1);
                                                     match = metabolismGene.test(item1);
                                                     if (!match) return false;


                                                     ItemStack item2 = pInput.getItem(2);
                                                     match = skeletalGene.test(item2);
                                                     if (!match) return false;


                                                     ItemStack item3 = pInput.getItem(3);
                                                     match = muscleGene.test(item3);
                                                     if (!match) return false;


                                                     ItemStack item4 = pInput.getItem(4);
                                                     match = skinGene.test(item4);
                                                     if (!match) return false;


                                                     ItemStack item5 = pInput.getItem(5);
                                                     match = respiratoryGene.test(item5);
                                                     if (!match) return false;


                                                     ItemStack item6 = pInput.getItem(6);
                                                     match = nervousSystemGene.test(item6);
                                                     if (!match) return false;


                                                     ItemStack item7 = pInput.getItem(7);
                                                     match = sensoryGene.test(item7);
                                                     if (!match) return false;


                                                     ItemStack item8 = pInput.getItem(8);
                                                     match = immunityGene.test(item8);;
                                                     if (!match) return false;


                                                     ItemStack item9 = pInput.getItem(9);
                                                     match = reproductiveGene.test(item9);
                                                     if (!match) return false;




                                                     ItemStack item13 = pInput.getItem(10);
                                                     match = reagent.test(item13);
                                                     if (!match) return false;


                                                     ItemStack item14 = pInput.getItem(11);
                                                     match = buffer.test(item14);
                                                     if (!match) return false;


                                                     return true;
                                                 }



    @Override
    public ItemStack assemble(DinosaurSplicingRecipeInput pInput, HolderLookup.Provider pRegistries) {return completedGenome.copy();}

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {return true;}

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {return completedGenome.copy();}

    @Override
    public RecipeSerializer<?> getSerializer() {return ModRecipes.DINO_DNA_SERIALIZER.get();}

    @Override
    public RecipeType<?> getType() {return ModRecipes.DINO_DNA_TYPE.get();}

                                public static class Serializer implements RecipeSerializer<DinosaurSplicingRecipe>{
                                public static final MapCodec<DinosaurSplicingRecipe> CODEC = RecordCodecBuilder.mapCodec(inst ->inst.group(
                                Ingredient.CODEC_NONEMPTY.fieldOf("dna1").forGetter(DinosaurSplicingRecipe::growthGene),
                                Ingredient.CODEC_NONEMPTY.fieldOf("dna2").forGetter(DinosaurSplicingRecipe::skeletalGene),
                                Ingredient.CODEC_NONEMPTY.fieldOf("dna3").forGetter(DinosaurSplicingRecipe::metabolismGene),
                                Ingredient.CODEC_NONEMPTY.fieldOf("dna4").forGetter(DinosaurSplicingRecipe::muscleGene),
                                Ingredient.CODEC_NONEMPTY.fieldOf("dna5").forGetter(DinosaurSplicingRecipe::skinGene),
                                Ingredient.CODEC_NONEMPTY.fieldOf("dna6").forGetter(DinosaurSplicingRecipe::respiratoryGene),
                                Ingredient.CODEC_NONEMPTY.fieldOf("dna7").forGetter(DinosaurSplicingRecipe::nervousSystemGene),
                                        Ingredient.CODEC_NONEMPTY.fieldOf("dna8").forGetter(DinosaurSplicingRecipe::sensoryGene),
                                        Ingredient.CODEC_NONEMPTY.fieldOf("dna9").forGetter(DinosaurSplicingRecipe::immunityGene),
                                        Ingredient.CODEC_NONEMPTY.fieldOf("dna10").forGetter(DinosaurSplicingRecipe::reproductiveGene),
                                                    Ingredient.CODEC_NONEMPTY.fieldOf("reagent").forGetter(DinosaurSplicingRecipe::reagent),
                                                    Ingredient.CODEC_NONEMPTY.fieldOf("buffer").forGetter(DinosaurSplicingRecipe::buffer),
                                                                ItemStack.CODEC.fieldOf("genomecompleted").forGetter(DinosaurSplicingRecipe::completedGenome)
                                                                                ).apply(inst, DinosaurSplicingRecipe::new));
                                public static final StreamCodec<RegistryFriendlyByteBuf, DinosaurSplicingRecipe> STREAM_CODEC =
                                        StreamCodec.of(
                                                (buf, recipe) -> {

                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.growthGene());
                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.skeletalGene());
                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.metabolismGene());
                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.muscleGene());
                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.skinGene());

                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.respiratoryGene());
                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.nervousSystemGene());
                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.sensoryGene());
                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.immunityGene());
                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.reproductiveGene());




                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.reagent());
                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.buffer());
                                                    ItemStack.STREAM_CODEC.encode(buf, recipe.completedGenome());



                                                },
                                                (buf) -> {
                                                    //Genes
                                                    Ingredient gen1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient gen2 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient gen3 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient gen4 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient gen5 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient gen6 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient gen7 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient gen8 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient gen9 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient gen10 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);


                                                    //buffers
                                                    Ingredient rea1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient buf1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    ItemStack fullseq = ItemStack.STREAM_CODEC.decode(buf);
                                                    return new DinosaurSplicingRecipe(gen1, gen2, gen3, gen4, gen5, gen6, gen7, gen8, gen9, gen10,rea1,buf1,fullseq);
                                                }
                                                );










                                @Override
                                    public MapCodec<DinosaurSplicingRecipe> codec() {return CODEC;}
                                    @Override
                                    public StreamCodec<RegistryFriendlyByteBuf, DinosaurSplicingRecipe> streamCodec() {return STREAM_CODEC;}




    }


}
