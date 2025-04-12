package net.shadowtek.fossilgencraft.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.shadowtek.fossilgencraft.item.ModItems;

public class SyringeBloodPigItem extends Item {
    public SyringeBloodPigItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return new ItemStack(ModItems.SYRINGE_CONTAMINATED.get());
    }
}

