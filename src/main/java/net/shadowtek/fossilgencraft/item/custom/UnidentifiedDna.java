package net.shadowtek.fossilgencraft.item.custom;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;


public class UnidentifiedDna extends Item {

    public UnidentifiedDna() {
        super(new Item.Properties());
    }



    // Get DNA completion based on current durability
    public static int getCompletion(ItemStack stack) {
        int damage = stack.getDamageValue();
        return 100 - damage;  // If damage is 0, completion is 100%
    }

}