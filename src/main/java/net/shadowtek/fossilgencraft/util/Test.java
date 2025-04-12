package net.shadowtek.fossilgencraft.util;


import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public class Test {
    public static void test(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos(); // Test if this method is recognized
        System.out.println(pos);
    }
}