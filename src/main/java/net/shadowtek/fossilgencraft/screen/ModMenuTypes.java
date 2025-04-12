package net.shadowtek.fossilgencraft.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.screen.custom.*;
import net.shadowtek.fossilgencraft.screen.custom.genesplicer.GeneSplicerComputerMenu;
public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, FossilGenCraft.MOD_ID);

    public static final RegistryObject<MenuType<AmberExtractorMenu>> AMBER_EXTRACTOR_MENU =
            MENUS.register("amber_extractor_name", () -> IForgeMenuType.create(AmberExtractorMenu::new));


    public static final RegistryObject<MenuType<CentrifugeMenu>> CENTRIFUGE_MENU =
            MENUS.register("centrifuge_name", () -> IForgeMenuType.create(CentrifugeMenu::new));

    public static final RegistryObject<MenuType<FreezeDryerMenu>> FREEZEDRYER_MENU =
            MENUS.register("freezedryer_name", () -> IForgeMenuType.create(FreezeDryerMenu::new));

    public static final RegistryObject<MenuType<DnaSequencerMenu>> DNA_SEQUENCER_MENU =
            MENUS.register("dna_sequencer_name", () -> IForgeMenuType.create(DnaSequencerMenu::new));

    public static final RegistryObject<MenuType<GeneSplicerComputerMenu>> GENE_SPLICER_MENU =
            MENUS.register("gene_splicer_computer_menu", () -> IForgeMenuType.create(GeneSplicerComputerMenu::new));

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}

