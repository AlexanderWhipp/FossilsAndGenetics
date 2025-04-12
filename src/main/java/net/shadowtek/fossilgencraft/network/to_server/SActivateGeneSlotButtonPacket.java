package net.shadowtek.fossilgencraft.network.to_server;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class SActivateGeneSlotButtonPacket {
    public SActivateGeneSlotButtonPacket() {
    }


    public SActivateGeneSlotButtonPacket(FriendlyByteBuf buffer) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }


    public void handle(CustomPayloadEvent.Context context){
        if(context.isServerSide()){
        ServerPlayer player = context.getSender();
    } else {
            context.setPacketHandled(false);
            System.err.println("Recieved Client Packet When Expecting Server!!! If you see this error something went very wrong!");
        }
    }
}


