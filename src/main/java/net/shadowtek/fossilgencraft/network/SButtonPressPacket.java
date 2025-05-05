package net.shadowtek.fossilgencraft.network;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;


public class SButtonPressPacket {
    public  SButtonPressPacket() {

    }

    public void encode(FriendlyByteBuf buffer) {

    }

    public SButtonPressPacket(FriendlyByteBuf buffer) {

    }

    public void handle(CustomPayloadEvent.Context context) {
        ServerPlayer player = context.getSender();
        if(player == null)
               return;
        player.sendSystemMessage(Component.literal("Packet Sent Successfully! by: " + player.getName()));
    }

}
