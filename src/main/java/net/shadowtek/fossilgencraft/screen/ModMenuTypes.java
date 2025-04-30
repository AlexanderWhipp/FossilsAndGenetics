package net.shadowtek.fossilgencraft.screen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.block.entity.custom.supercomputer.SuperComputerTerminalBlockEntity;
import net.shadowtek.fossilgencraft.screen.custom.*;
import net.shadowtek.fossilgencraft.screen.custom.supercomputer.SuperComputerMenu;

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

    public static final RegistryObject<MenuType<GeneSplicerMenu>> GENE_SPLICER_MENU =
            MENUS.register("gene_splicer_computer_menu", () -> IForgeMenuType.create(GeneSplicerMenu::new));
    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }

    public static final RegistryObject<MenuType<SuperComputerMenu>> SUPERCOMPUTER_MENU =
            MENUS.register("supercomputer_menu", () -> IForgeMenuType.create(
                    // This is the factory that creates the Menu instance, potentially using buffer data
                    (windowId, inv, buf) -> {
                        // Read BlockPos sent via network when menu opened remotely
                        BlockPos pos = buf.readBlockPos();
                        // Get the Client-side BE instance
                        BlockEntity be = inv.player.level().getBlockEntity(pos);
                        // Check type and create menu
                        if (be instanceof SuperComputerTerminalBlockEntity scbe) {
                            // Pass the BE instance AND its ContainerData accessor
                            return new SuperComputerMenu(windowId, inv, scbe, scbe.dataAccess);
                        }
                        // Fallback if BE is wrong type or missing on client
                        // Returning null can cause issues, throwing might be better? Or return a dummy menu?
                        FossilGenCraft.LOGGER.error("Failed to create SupercomputerMenu: Incorrect BlockEntity at {}!", pos);
                        // Returning null might crash, let's throw for clarity during dev
                        throw new IllegalStateException("Incorrect BlockEntity type at pos " + pos + " for SupercomputerMenu!");
                    }
            ));
}

