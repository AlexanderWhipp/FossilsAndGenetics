package net.shadowtek.fossilgencraft.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AmberExtractorRecipeInput(ItemStack input_1, ItemStack input_2, ItemStack input_3, ItemStack input_4) implements RecipeInput {
    @Override
    public ItemStack getItem(int pIndex) {
        return switch (pIndex){
            case 0 -> this.input_1;
            case 1 -> this.input_2;
            case 2 -> this.input_3;
            case 3 -> this.input_4;
            default -> ItemStack.EMPTY;
        };

    }

    @Override
    public int size() {
        return 4;
    }
}
