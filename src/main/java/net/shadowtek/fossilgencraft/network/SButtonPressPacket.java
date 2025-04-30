package net.shadowtek.fossilgencraft.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.shadowtek.fossilgencraft.block.entity.custom.supercomputer.SuperComputerTerminalBlockEntity;

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


    //   if (context.isClientSide()) {
     //      LocalPlayer player = Minecraft.getInstance().player;
    //   }
    }

}
