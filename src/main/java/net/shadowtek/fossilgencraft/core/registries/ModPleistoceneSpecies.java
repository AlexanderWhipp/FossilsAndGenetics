package net.shadowtek.fossilgencraft.core.registries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.data.species.dinosaurs.DinosaurSpecies;
import net.shadowtek.fossilgencraft.data.species.dinosaurs.fixedtraits.DinosaurDiet;
import net.shadowtek.fossilgencraft.data.species.dinosaurs.fixedtraits.DinosaurPeriod;
import net.shadowtek.fossilgencraft.data.species.pleistocene.PleistoceneSpecies;
import net.shadowtek.fossilgencraft.data.species.pleistocene.fixedtraits.PleistoceneDiet;
import net.shadowtek.fossilgencraft.data.species.pleistocene.fixedtraits.PleistocenePeriod;

import java.util.function.Supplier;

public class ModPleistoceneSpecies {


    public static final ResourceKey<Registry<PleistoceneSpecies>> PLEISTOCENE_SPECIES_REGISTRY_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "pleistocene_species"));

    public static final DeferredRegister<PleistoceneSpecies> PLEISTOCENE_SPECIES_DEFERRED_REGISTER =
            DeferredRegister.create(PLEISTOCENE_SPECIES_REGISTRY_KEY, FossilGenCraft.MOD_ID);

    public static final Supplier<IForgeRegistry<PleistoceneSpecies>> PLEISTOCENE_SPECIES_CATEGORY =
            PLEISTOCENE_SPECIES_DEFERRED_REGISTER.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<PleistoceneSpecies> WOOLLY_MAMMOTH = PLEISTOCENE_SPECIES_DEFERRED_REGISTER.register("woolly_mammoth",
            () -> new PleistoceneSpecies(PleistoceneDiet.HERBIVORE, PleistocenePeriod.LATE_PLEISTOCENE)
    );









    public static void register(IEventBus eventBus) {
        PLEISTOCENE_SPECIES_DEFERRED_REGISTER.register(eventBus);
    }
}
