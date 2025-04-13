package net.shadowtek.fossilgencraft;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.shadowtek.fossilgencraft.block.ModBlocks;
import net.shadowtek.fossilgencraft.block.entity.ModBlockEntities;
import net.shadowtek.fossilgencraft.block.entity.renderer.AmberExtractorBlockEntityRenderer;
import net.shadowtek.fossilgencraft.core.registries.ModDinosaurSpecies;
import net.shadowtek.fossilgencraft.entity.ModEntities;
import net.shadowtek.fossilgencraft.entity.client.TRex001Renderer;
import net.shadowtek.fossilgencraft.event.ModDataComponents;
import net.shadowtek.fossilgencraft.event.ModLootEvents;
import net.shadowtek.fossilgencraft.item.ModCreativeModeTabs;
import net.shadowtek.fossilgencraft.item.ModItems;
import net.shadowtek.fossilgencraft.recipe.ModRecipes;
import net.shadowtek.fossilgencraft.screen.ModMenuTypes;
import net.shadowtek.fossilgencraft.screen.custom.*;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FossilGenCraft.MOD_ID)
public class FossilGenCraft {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "fossilgencraft";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();



    public FossilGenCraft(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModDinosaurSpecies.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModLootEvents.register(modEventBus);

        ModDataComponents.register(modEventBus);
        ModBlockEntities.register(modEventBus);


        ModMenuTypes.register(modEventBus);
        ModRecipes.register(modEventBus);



        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        LOGGER.info("{} Setup Complete!",FossilGenCraft.MOD_ID);
        //Networking.registerMessages();
    }


    private void commonSetup(final FMLCommonSetupEvent event){



    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event){
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.AMBER);
            event.accept(ModItems.FOSSIL_IMPRINT);
            event.accept(ModItems.TEST_TUBE_EMPTY);
            event.accept(ModItems.TEST_TUBE_WATER);
        }
        if(event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(ModBlocks.AMBER_ORE);
            event.accept(ModBlocks.DEEPSLATE_AMBER_ORE);
            event.accept(ModBlocks.FOSSIL_ORE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event){
            EntityRenderers.register(ModEntities.TREX001.get(), TRex001Renderer::new);

            MenuScreens.register(ModMenuTypes.AMBER_EXTRACTOR_MENU.get(), AmberScreen::new);
            MenuScreens.register(ModMenuTypes.CENTRIFUGE_MENU.get(), CentrifugeScreen::new);
            MenuScreens.register(ModMenuTypes.FREEZEDRYER_MENU.get(), FreezeDryerScreen::new);
            MenuScreens.register(ModMenuTypes.DNA_SEQUENCER_MENU.get(), DnaSequencerScreen::new);
            MenuScreens.register(ModMenuTypes.GENE_SPLICER_MENU.get(), GeneSplicerScreen::new);

            System.err.println("Registered GeneSplicerScreen for client");


        }
        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.AMBER_EXTRACTOR_BE.get(), AmberExtractorBlockEntityRenderer::new);

        }
    }
}
