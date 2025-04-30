package net.shadowtek.fossilgencraft.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;
import net.shadowtek.fossilgencraft.FossilGenCraft;

public class NetworkHandler {
    private static final SimpleChannel INSTANCE = ChannelBuilder.named(
                    ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "main"))
            .serverAcceptedVersions((status, version) -> true)
            .clientAcceptedVersions((status, version) -> true)
            .networkProtocolVersion(1)
            .simpleChannel();
// OKAY FROM ALL MY RESEARCH THIS IS HOW A CHANNEL IS MADE, so the problem is not here!.

    public static void register() {
        INSTANCE.messageBuilder(SButtonPressPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SButtonPressPacket::encode)
                .decoder(SButtonPressPacket::new)
                .consumerMainThread(SButtonPressPacket::handle)
                .add();

    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }

    public static void sendToPlayer(Object msg, ServerPlayer player) {
        INSTANCE.send(msg, PacketDistributor.PLAYER.with(player));
    }

    public static void serverToAllClients(Object msg){
        INSTANCE.send(msg, PacketDistributor.ALL.noArg());
    }


}