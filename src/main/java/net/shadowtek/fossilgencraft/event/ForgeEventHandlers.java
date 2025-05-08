package net.shadowtek.fossilgencraft.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.data.loader.DnaExtractionManager;
import net.shadowtek.fossilgencraft.data.loader.GeneOneAssignmentManager;
import net.shadowtek.fossilgencraft.data.loader.GeneTwoAssignmentManager;
import net.shadowtek.fossilgencraft.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = FossilGenCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandlers {
    @SubscribeEvent // Marks this method as an event listener
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        // This method will be called automatically by Forge when it's time
        // to add reload listeners (during server start, /reload command).

        event.addListener(new DnaExtractionManager());
        event.addListener(new GeneOneAssignmentManager());
        event.addListener(new GeneTwoAssignmentManager());
        FossilGenCraft.LOGGER.info("Registered DnaExtractionManager to reload listeners.");
        FossilGenCraft.LOGGER.info("Registered GeneOneAssignmentManager to reload listeners.");
        FossilGenCraft.LOGGER.info("Registered GeneTwoAssignmentManager to reload listeners");
    }

}
