package net.shadowtek.fossilgencraft.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.entity.ModEntities;
import net.shadowtek.fossilgencraft.item.custom.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, FossilGenCraft.MOD_ID);
//Fossil Items
    public static final RegistryObject<Item> AMBER = ITEMS.register("amber",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> FOSSIL_IMPRINT = ITEMS.register( "fossil_imprint",
            () -> new Item(new Item.Properties().stacksTo(64)));

    //Test Tubes
    public static final RegistryObject<Item> TEST_TUBE_WATER = ITEMS.register( "test_tube_water",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> TEST_TUBE_EMPTY = ITEMS.register( "test_tube_empty",
            () -> new TestTubeItem(new Item.Properties().stacksTo(64), ModItems.TEST_TUBE_WATER.get()));
    public static final RegistryObject<Item> TEST_TUBE_BLOOD_PIG = ITEMS.register( "test_tube_blood_pig",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> TEST_TUBE_BLOOD_CHICKEN = ITEMS.register( "test_tube_blood_chicken",
            () -> new Item(new Item.Properties().stacksTo(1)));

   //Syringes
    public static final RegistryObject<Item> SYRINGE_EMPTY = ITEMS.register("syringe_empty",
           () -> new SyringeItem(new Item.Properties().stacksTo(16)));
    //Syringes--USED
    public static final RegistryObject<Item> SYRINGE_FILLED_BLOOD = ITEMS.register("syringe_filled_blood",
            () -> new SyringeBloodChickenItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SYRINGE_BLOOD_PIG = ITEMS.register("syringe_blood_pig",
            () -> new SyringeFilledBlood(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SYRINGE_CONTAMINATED = ITEMS.register("syringe_contaminated",
            () -> new Item(new Item.Properties().stacksTo(16)));

    //crafting Items
    public static final RegistryObject<Item> AMBER_SHARD = ITEMS.register("amber_shard",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> OSTRICH_EGG = ITEMS.register("ostrich_egg",
            () -> new Item(new Item.Properties().stacksTo(16)));


    //Centrifuge Items
    public static final RegistryObject<Item> CHEMICAL_LYSIS_BUFFER = ITEMS.register("chemical_lysis_buffer",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public  static final RegistryObject<Item> CONTAMINANT_RESIDUE = ITEMS.register("contaminant_residue",
            () -> new Item(new Item.Properties().stacksTo(64)));

    //ENZYMES
    public  static final RegistryObject<Item> ENZYME_POWDER = ITEMS.register("enzyme_powder",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> ENZYME_RESIDUE = ITEMS.register("enzyme_residue",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> FUNGAL_ENZYME_SAMPLE = ITEMS.register("fungal_enzyme_sample",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> ENZYME_RESIDUE_DRIED = ITEMS.register("enzyme_residue_dried",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> MORTAR_AND_PESTLE = ITEMS.register("mortar_and_pestle",
            () -> new MortarAndPestleItem(new Item.Properties().stacksTo(1)));

    //Detergent
    public static final RegistryObject<Item> DETERGENT = ITEMS.register("detergent",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> PIG_FAT = ITEMS.register("pig_fat",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur",
            () -> new Item(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> ASH = ITEMS.register("ash",
            ()-> new Item(new Item.Properties().stacksTo(64)));

    //Sequencing Items
    public static final RegistryObject<Item> SEQUENCING_REAGENT = ITEMS.register("sequencing_reagent",
            () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> FLOURESCENT_GEL = ITEMS.register("flourescent_gel",
            () -> new Item(new Item.Properties().stacksTo(16)));

//DNA
    public  static final RegistryObject<Item> CONTAMINATED_MOSQUITO_SAMPLE = ITEMS.register("contaminated_mosquito_sample",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public  static final RegistryObject<Item> PURIFIED_MOSQUITO_SAMPLE = ITEMS.register("purified_mosquito_sample",
            () ->new  Item(new Item.Properties().stacksTo(1)));
    public  static final RegistryObject<Item> UNSEQUENCED_DNA_CONCENTRATE = ITEMS.register("unsequenced_dna_concentrate",
            () ->new Item(new Item.Properties().stacksTo(1)));




    public static final RegistryObject<Item> DNA_SAMPLE_PIG = ITEMS.register( "dna_sample_pig",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DNA_SAMPLE_CHICKEN = ITEMS.register( "dna_sample_chicken",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public  static final RegistryObject<Item> COMPLETED_GENOME = ITEMS.register("completed_genome",
            () ->new CompletedDinosaurGenomeItem(new Item.Properties().stacksTo(1)));


    public  static final RegistryObject<Item> EGG_CELL = ITEMS.register("egg_cell",
            () ->new Item(new Item.Properties().stacksTo(64)));
    public  static final RegistryObject<Item> EMBRYO = ITEMS.register("embryo",
            () ->new Item(new Item.Properties().stacksTo(1)));

    public  static final RegistryObject<Item> SEQUENCED_DNA_BASE_CHAIN = ITEMS.register("sequenced_dna_base_chain",
            () ->new SequencedDnaBaseChainItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SUPER_COMPUTER_HARD_DRIVE = ITEMS.register("super_computer_hard_drive",
    () -> new SuperComputerHardDrive(new Item.Properties().stacksTo(1)));





        //OlD Test Item, may revert back if SEQUENCED_DNA_BASE_CHAIN doesn't pan out
  /*  public static final RegistryObject<Item> UNIDENTIFIED_DNA_SAMPLE = ITEMS.register("unidentified_dna_sample",
            () -> new Item(new Item.Properties().durability(100).stacksTo(1)) {
                @Override
                public boolean isBarVisible(ItemStack pStack) {
                    return true; //Always Show Visibility Bar


                }
                @Override
                public String getDescriptionId(ItemStack stack) {
                    int damage = stack.getDamageValue();
                    int maxDamage = stack.getMaxDamage();
                    float percentage = 1.0f - (float) damage / maxDamage;

                    if (percentage > 0.81f) {
                        return super.getDescriptionId(stack); //Undentified(as seen in crafting table output)
                    } else if (percentage > 0.8f) {
                        return super.getDescriptionId(stack) + ".best";
                    } else if (percentage > 0.6f) {
                        return super.getDescriptionId(stack) + ".very_high"; // Override with "Very_High_Quality T.rex Genome" state
                    } else if (percentage > 0.5f) {
                        return super.getDescriptionId(stack) + ".high"; // Override with "High_Quality_Late_Cretaceous_Therapod_Genome" state
                    } else if (percentage > 0.4f) {
                        return super.getDescriptionId(stack) + ".medium"; //"Medium_Quality_Late_Cretaceous_Genome" state
                    } else if (percentage > 0.3f) {
                        return super.getDescriptionId(stack) + ".low"; // Override with "Low_Quality_Cretaceous_Genome" state

                    } else {
                        return super.getDescriptionId(stack) + ".poor"; // Override with "Unidentified_Dinosaur_DNA" state
                    }
                }

            }); */
//Spawn Eggs
    public static final RegistryObject<Item> TREX_SPAWN_EGG = ITEMS.register("trex_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.TREX001,0x53524b, 0xdac741, new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
