package net.shadowtek.fossilgencraft.event;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.block.ModBlocks;
import net.shadowtek.fossilgencraft.item.ModItems;
import net.shadowtek.fossilgencraft.network.PacketHandler;

public class ClientModEvents {

    @Mod.EventBusSubscriber(modid = FossilGenCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ClientEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.AMBER_GEM_INSECT.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.AMBER_GEM_FLAWLESS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.AMBER_LAMP.get(), RenderType.translucent());
        }
        @SubscribeEvent
        public static void CommonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() ->  {

                PacketHandler.register();



            });




        }

    }
}
