package net.shadowtek.fossilgencraft.core.registries;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.data.species.dinosaurs.fixedtraits.DinosaurDiet;
import net.shadowtek.fossilgencraft.data.species.dinosaurs.DinosaurSpecies;
import net.minecraft.core.Registry;
import net.shadowtek.fossilgencraft.data.species.dinosaurs.fixedtraits.DinosaurPeriod;

import java.util.function.Supplier;

public class ModDinosaurSpecies {

    public static final ResourceKey<Registry<DinosaurSpecies>> DINOSAUR_SPECIES_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "species"));

    public static final DeferredRegister<DinosaurSpecies> DINOSAUR_SPECIES_DEFERRED_REGISTER =
            DeferredRegister.create(DINOSAUR_SPECIES_REGISTRY_KEY, FossilGenCraft.MOD_ID);

    public static final Supplier<IForgeRegistry<DinosaurSpecies>> DINOSAUR_SPECIES_CATEGORY =
            DINOSAUR_SPECIES_DEFERRED_REGISTER.makeRegistry(RegistryBuilder::new);

    //NEW DINOSAUR REGISTRATION PROCESS STEP 1: Follow the bellow format to register a species to the Mod. -> refer to DinosaurSpecies.java in the Data pack to proceed to step 2
public static final RegistryObject<DinosaurSpecies> TYRANNOSAURUS_REX = DINOSAUR_SPECIES_DEFERRED_REGISTER.register("tyrannosaurus_rex",
        () -> new DinosaurSpecies(DinosaurDiet.CARNIVORE, DinosaurPeriod.CRETACIOUS)
            );
public static final RegistryObject<DinosaurSpecies> SPINOSAURUS = DINOSAUR_SPECIES_DEFERRED_REGISTER.register("spinosaurus",
        () -> new DinosaurSpecies(DinosaurDiet.CARNIVORE, DinosaurPeriod.CRETACIOUS)
);
public static final RegistryObject<DinosaurSpecies> ALLOSAURUS = DINOSAUR_SPECIES_DEFERRED_REGISTER.register("allosaurus",
        () -> new DinosaurSpecies(DinosaurDiet.CARNIVORE,DinosaurPeriod.JURASSIC)
            );






    public static void register(IEventBus eventBus) {
        DINOSAUR_SPECIES_DEFERRED_REGISTER.register(eventBus);
    }
}