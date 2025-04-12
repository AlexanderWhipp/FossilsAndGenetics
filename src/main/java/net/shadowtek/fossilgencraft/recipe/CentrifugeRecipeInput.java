package net.shadowtek.fossilgencraft.recipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CentrifugeRecipeInput(ItemStack input1, ItemStack input2) implements RecipeInput {
    @Override
    public ItemStack getItem(int pIndex) {
        return switch (pIndex){
            case 0 -> this.input1;
            case 1 -> this.input2;
            default -> ItemStack.EMPTY;
        };
    }
    @Override
    public int size() {
        return 2;
    }
}
