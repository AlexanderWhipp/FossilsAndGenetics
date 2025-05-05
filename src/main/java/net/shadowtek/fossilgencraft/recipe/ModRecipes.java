package net.shadowtek.fossilgencraft.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.recipe.splicing.dinosaurs.DinosaurSplicingRecipe;


public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FossilGenCraft.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, FossilGenCraft.MOD_ID);

    public static final RegistryObject<RecipeSerializer<CentrifugeRecipe>> CENTRIFUGE_SERIALIZER =
            SERIALIZERS.register("centrifuge", CentrifugeRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<CentrifugeRecipe>> CENTRIFUGE_TYPE =
            TYPES.register("centrifuge", () -> new RecipeType<CentrifugeRecipe>() {
                @Override
                public String toString(){
                    return "centrifuge";
                }
            });
    public static final RegistryObject<DnaSplittingCentrifuge.Serializer> CENTRIFUGE_SPLITTING_SERIALIZER =
            SERIALIZERS.register("centrifuge_splitting", DnaSplittingCentrifuge.Serializer::new);
    public static final RegistryObject<RecipeType<DnaSplittingCentrifuge>> CENTRIFUGE_SPLITTING_TYPE =
            TYPES.register("centrifuge_splitting", () -> new RecipeType<DnaSplittingCentrifuge>() {
                @Override
                public String toString(){
                    return "centrifuge_splitting";
                }
            });

    public static final RegistryObject<RecipeSerializer<FreezeDryerRecipe>> FREEZEDRYER_SERIALIZER =
            SERIALIZERS.register("freezedryer", FreezeDryerRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<FreezeDryerRecipe>> FREEZEDRYER_TYPE =
            TYPES.register("freezedryer", () -> new RecipeType<FreezeDryerRecipe>() {
                @Override
                public String toString(){
                    return "freezedryer";
                }
            });
    public static final RegistryObject<RecipeSerializer<DnaSequencerRecipe>> DNA_SEQUENCER_SERIALIZER =
            SERIALIZERS.register("dna_sequencer", DnaSequencerRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<DnaSequencerRecipe>> DNA_SEQUENCER_TYPE =
            TYPES.register("dna_sequencer", () -> new RecipeType<DnaSequencerRecipe>() {
                @Override
                public String toString(){
                    return "dna_sequencer";
                }
            });

    public static final RegistryObject<RecipeSerializer<AmberExtractorRecipe>> AMBER_EXTRACTOR_SERIALIZER =
            SERIALIZERS.register("amber_extractor", AmberExtractorRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<AmberExtractorRecipe>> AMBER_EXTRACTOR_TYPE =
            TYPES.register("amber_extractor", () -> new RecipeType<AmberExtractorRecipe>() {
                @Override
                        public String toString(){
                    return "amber_extractor";
                }

            });


    public static final RegistryObject<RecipeSerializer<DinosaurSplicingRecipe>> DINO_DNA_SERIALIZER =
            SERIALIZERS.register("dino_splicing", DinosaurSplicingRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<DinosaurSplicingRecipe>> DINO_DNA_TYPE =
            TYPES.register("dino_splicing", () -> new RecipeType<DinosaurSplicingRecipe>() {
                @Override
                public String toString(){
                    return "dino_splicing";
                }

            });



    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }

}
