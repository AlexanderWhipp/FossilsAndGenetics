package net.shadowtek.fossilgencraft.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.block.ModBlocks;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FossilGenCraft.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FOSSILGENCRAFT_ITEMS_TAB = CREATIVE_MODE_TABS.register( "fossilgencraft_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.AMBER.get()))
                    .title(Component.translatable( "creativetab.fossilgencraft.fossilgencraft_items"))
                    .displayItems((itemDisplayParameters, output) ->{
                        output.accept(ModItems.FOSSIL_IMPRINT.get());
//Test Tubes
                        output.accept(ModItems.TEST_TUBE_EMPTY.get());
                        output.accept(ModItems.TEST_TUBE_WATER.get());
                        output.accept(ModItems.TEST_TUBE_BLOOD_PIG.get());
                        output.accept(ModItems.TEST_TUBE_BLOOD_CHICKEN.get());
//Vanilla mob Dna
                        output.accept(ModItems.DNA_SAMPLE_CHICKEN.get());
                        output.accept(ModItems.DNA_SAMPLE_PIG.get());
                  //Dino-DNA
                        output.accept(ModItems.SEQUENCED_DNA_BASE_CHAIN.get());
                        output.accept(ModItems.COMPLETED_GENOME.get());
                        output.accept(ModItems.UNSEQUENCED_DNA_CONCENTRATE.get());
                        output.accept(ModItems.CONTAMINATED_MOSQUITO_SAMPLE.get());
                        output.accept(ModItems.PURIFIED_MOSQUITO_SAMPLE.get());
// Lysis Buffer Items
                        output.accept(ModItems.CHEMICAL_LYSIS_BUFFER.get());
                        output.accept(ModItems.SEQUENCING_REAGENT.get());
                        output.accept(ModItems.DETERGENT.get());
                        output.accept(ModItems.ASH.get());
                        output.accept(ModItems.SULFUR.get());
                        output.accept(ModItems.PIG_FAT.get());
                        output.accept(ModItems.ENZYME_POWDER.get());
                        output.accept(ModItems.ENZYME_RESIDUE.get());
                        output.accept(ModItems.ENZYME_RESIDUE_DRIED.get());
                        output.accept(ModItems.FUNGAL_ENZYME_SAMPLE.get());
                        output.accept(ModItems.MORTAR_AND_PESTLE.get());

                   //Dinosaur Egg Items
                        output.accept(ModItems.EGG_CELL.get());
                        output.accept(ModItems.EMBRYO.get());
                        output.accept(ModItems.OSTRICH_EGG.get());
//Syringes
                        output.accept(ModItems.SYRINGE_EMPTY.get());
                        output.accept(ModItems.SYRINGE_BLOOD_CHICKEN.get());
                        output.accept(ModItems.SYRINGE_BLOOD_PIG.get());
                        output.accept(ModItems.SYRINGE_CONTAMINATED.get());
//Junk Items
                        output.accept(ModItems.AMBER_SHARD.get());
                        output.accept(ModItems.CONTAMINANT_RESIDUE.get());



                        output.accept(ModItems.TREX_SPAWN_EGG.get());

                        output.accept(ModBlocks.AMBER_GEM_INSECT.get());
                        output.accept(ModBlocks.AMBER_GEM_FLAWLESS.get());
                    }).build());
    public static final RegistryObject<CreativeModeTab> FOSSILGENCRAFT_BlOCKS_TAB = CREATIVE_MODE_TABS.register( "fossilgencraft_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.AMBER_ORE.get()))
                    .withTabsBefore(FOSSILGENCRAFT_ITEMS_TAB.getId())
                    .title(Component.translatable( "creativetab.fossilgencraft.fossilgencraft_blocks"))
                    .displayItems((itemDisplayParameters, output) ->{
                        output.accept(ModBlocks.AMBER_ORE.get());
                        output.accept(ModBlocks.DEEPSLATE_AMBER_ORE.get());
                        output.accept(ModBlocks.FOSSIL_ORE.get());
                        output.accept(ModBlocks.NETHER_SULFUR_ORE.get());

                        output.accept(ModBlocks.AMBER_LAMP.get());

                        output.accept(ModBlocks.AMBER_EXTRACTOR.get());
                        output.accept(ModBlocks.CENTRIFUGE.get());
                        output.accept(ModBlocks.FREEZEDRYER.get());

                        output.accept(ModBlocks.GENE_SPLICER_COMPUTER.get());

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
