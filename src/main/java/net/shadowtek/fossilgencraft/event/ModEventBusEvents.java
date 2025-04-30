package net.shadowtek.fossilgencraft.event;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.entity.ModEntities;
import net.shadowtek.fossilgencraft.entity.client.TRex001Model;
import net.shadowtek.fossilgencraft.entity.custom.TRex001Entity;
import net.shadowtek.fossilgencraft.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = FossilGenCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
 public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TRex001Model.LAYER_LOCATION, TRex001Model::createBodyLayer);

    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(ModEntities.TREX001.get(), TRex001Entity.createAttributes().build());
    }
    @SubscribeEvent
    public static void commonsetup(FMLCommonSetupEvent event){
        event.enqueueWork( () -> {
            NetworkHandler.register();
        });
    }
}
