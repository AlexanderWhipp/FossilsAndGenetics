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
                                     Ingredient immunityGene, Ingredient reproductiveGene, Ingredient cognitionGene, Ingredient ageGene,Ingredient vascularGene, Ingredient reagent, Ingredient buffer,
                                     ItemStack completedGenome) implements Recipe<DinosaurSplicingRecipeInput> {
    @Override
    public NonNullList<Ingredient> getIngredients() {
        System.err.println("Task1 Init: Getting Gene Splicer Ingrediants...");
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(growthGene);list.add(metabolismGene);list.add(skeletalGene);list.add(muscleGene);list.add(skinGene);
        list.add(respiratoryGene);list.add(nervousSystemGene);list.add(sensoryGene);list.add(immunityGene);
        list.add(reproductiveGene);list.add(cognitionGene);list.add(ageGene);list.add(vascularGene); list.add(reagent);list.add(buffer);
        System.err.println("Task1 Com: Successfully retrieved" + list + "Total Genes Detected:" +(long) list.size());
        return list;
                                                     }//All FOSSILGENCRAFT CUSTOM RECIPES TO FOLLOW THIS LAYOUT FORMAT, HELPS KEEP READABILITY
                                                        //Escpecially for recipes like this one which has not yet been optimised to loop its inputs
                                                 @Override
                                                 public boolean matches(DinosaurSplicingRecipeInput pInput, Level pLevel) {
                                                 if(pLevel.isClientSide()) {
                                                     return false;
                                                 }
                                                     if (pInput.size() != 15) {
                                                         FossilGenCraft.LOGGER.warn("Recipe Match Failed: Input size was {}, expected 15", pInput.size());
                                                         return false;
                                                     }



                                                     System.err.println("--- Starting Splicing Match Check ---");

                                                     boolean match; // Variable to hold test result

                                                     // Slot 0 - Growth Gene
                                                     ItemStack item0 = pInput.getItem(0);
                                                     match = growthGene.test(item0);
                                                     System.err.println("Slot 0 (Growth): Item={}, Match={}"+ item0 + match);
                                                     if (!match) return false;

                                                     // Slot 1 - Metabolism Gene
                                                     ItemStack item1 = pInput.getItem(1);
                                                     match = metabolismGene.test(item1);
                                                     System.err.println("Slot 1 (Metabolism): Item={}, Match={}" + item1 + match);
                                                     if (!match) return false;

                                                     // Slot 2 - Skeletal Gene
                                                     ItemStack item2 = pInput.getItem(2);
                                                     match = skeletalGene.test(item2);
                                                     System.err.println("Slot 2 (Skeletal): Item={}, Match={}" + item2 + match);
                                                     if (!match) return false;

                                                     // Slot 3 - Muscle Gene
                                                     ItemStack item3 = pInput.getItem(3);
                                                     match = muscleGene.test(item3);
                                                     System.err.println("Slot 3 (Muscle): Item={}, Match={}" + item3 + match);
                                                     if (!match) return false;

                                                     // Slot 4 - Skin Gene
                                                     ItemStack item4 = pInput.getItem(4);
                                                     match = skinGene.test(item4);
                                                     System.err.println("Slot 4 (Skin): Item={}, Match={}" + item4 + match);
                                                     if (!match) return false;

                                                     // Slot 5 - Respiratory Gene
                                                     ItemStack item5 = pInput.getItem(5);
                                                     match = respiratoryGene.test(item5);
                                                     System.err.println("Slot 5 (Respiratory): Item={}, Match={}" + item5 + match);
                                                     if (!match) return false;

                                                     // Slot 6 - Nervous System Gene
                                                     ItemStack item6 = pInput.getItem(6);
                                                     match = nervousSystemGene.test(item6);
                                                     System.err.println("Slot 6 (Nervous System): Item={}, Match={}" + item6 + match);
                                                     if (!match) return false;

                                                     // Slot 7 - Sensory Gene
                                                     ItemStack item7 = pInput.getItem(7);
                                                     match = sensoryGene.test(item7);
                                                     System.err.println("Slot 7 (Sensory): Item={}, Match={}"+ item7 + match);
                                                     if (!match) return false;

                                                     // Slot 8 - Immunity Gene
                                                     ItemStack item8 = pInput.getItem(8);
                                                     match = immunityGene.test(item8);
                                                     System.err.println("Slot 8 (Immunity): Item={}, Match={}"+ item8 + match);
                                                     if (!match) return false;

                                                     // Slot 9 - Reproductive Gene
                                                     ItemStack item9 = pInput.getItem(9);
                                                     match = reproductiveGene.test(item9);
                                                     System.err.println("Slot 9 (Reproductive): Item={}, Match={}" + item9 + match);
                                                     if (!match) return false;

                                                     // Slot 10 - Cognition Gene
                                                     ItemStack item10 = pInput.getItem(10);
                                                     match = cognitionGene.test(item10);
                                                     System.err.println("Slot 10 (Cognition): Item={}, Match={}" + item10 + match);
                                                     if (!match) return false;

                                                     // Slot 11 - Age Gene
                                                     ItemStack item11 = pInput.getItem(11);
                                                     match = ageGene.test(item11);
                                                     System.err.println("Slot 11 (Age): Item={}, Match={}" + item11 + match);
                                                     if (!match) return false;

                                                     // Slot 12 - Vascular Gene
                                                     ItemStack item12 = pInput.getItem(12);
                                                     match = vascularGene.test(item12);
                                                     System.err.println("Slot 12 (Vascular): Item={}, Match={}" + item12 + match);
                                                     if (!match) return false;

                                                     // Slot 13 - Reagent
                                                     ItemStack item13 = pInput.getItem(13);
                                                     match = reagent.test(item13);
                                                     System.err.println("Slot 13 (Reagent): Item={}, Match={}" + item13 + match);
                                                     if (!match) return false;

                                                     // Slot 14 - Buffer
                                                     ItemStack item14 = pInput.getItem(14);
                                                     match = buffer.test(item14);
                                                     System.err.println("Slot 14 (Buffer): Item={}, Match={}" + item14 + match);
                                                     if (!match) return false;


                                                     System.err.println("--- Splicing Match Check SUCCESSFUL ---");
                                                     return true; // Only reaches here if all tests passed
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
                                        Ingredient.CODEC_NONEMPTY.fieldOf("dna11").forGetter(DinosaurSplicingRecipe::cognitionGene),
                                        Ingredient.CODEC_NONEMPTY.fieldOf("dna12").forGetter(DinosaurSplicingRecipe::ageGene),
                                        Ingredient.CODEC_NONEMPTY.fieldOf("dna13").forGetter(DinosaurSplicingRecipe::vascularGene),
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
                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.cognitionGene());
                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.ageGene());
                                                    Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.vascularGene());

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
                                                    Ingredient gen11 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient gen12 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient gen13 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);

                                                    //buffers
                                                    Ingredient rea1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    Ingredient buf1 = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);
                                                    ItemStack fullseq = ItemStack.STREAM_CODEC.decode(buf);
                                                    return new DinosaurSplicingRecipe(gen1, gen2, gen3, gen4, gen5, gen6, gen7, gen8, gen9, gen10,gen11,gen12,gen13,rea1,buf1,fullseq);
                                                }
                                                );










                                @Override
                                    public MapCodec<DinosaurSplicingRecipe> codec() {return CODEC;}
                                    @Override
                                    public StreamCodec<RegistryFriendlyByteBuf, DinosaurSplicingRecipe> streamCodec() {return STREAM_CODEC;}




    }


}
