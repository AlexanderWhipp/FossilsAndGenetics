package net.shadowtek.fossilgencraft.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.*;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.network.to_server.SActivateGeneSlotButtonPacket;
//this has been setup ready for use in the lategame multiblock DNA processing - serves no current purpose, consider commenting out
//needed
public class PacketHandler {
    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
            ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "main"))
            .serverAcceptedVersions((status, version) -> true)
            .clientAcceptedVersions((status, version) -> true)
            .networkProtocolVersion(1)
            .simpleChannel();


    public static void register(){
        INSTANCE.messageBuilder(SActivateGeneSlotButtonPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SActivateGeneSlotButtonPacket::encode)
             //   .decoder(SActivateGeneSlotButtonPacket::handle)
                .consumerMainThread(SActivateGeneSlotButtonPacket::handle)
                .add();
    }
    public static void sendToServer(Object msg){
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }
    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(msg, PacketDistributor.PLAYER.with(player));
    }
    public static void SendToAllClients(Object msg) {
        INSTANCE.send(msg, PacketDistributor.ALL.noArg());
    }
}
