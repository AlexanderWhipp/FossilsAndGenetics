package net.shadowtek.fossilgencraft.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;


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
                    return "dnasequencer";
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




    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }

}
