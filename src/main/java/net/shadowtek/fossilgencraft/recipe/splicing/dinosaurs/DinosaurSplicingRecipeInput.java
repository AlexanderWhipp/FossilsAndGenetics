package net.shadowtek.fossilgencraft.recipe.splicing.dinosaurs;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record DinosaurSplicingRecipeInput(ItemStack base1,ItemStack base2,ItemStack base3,ItemStack base4,ItemStack base5,
                                          ItemStack base6,ItemStack base7,ItemStack base8,ItemStack base9,ItemStack base10,
                                          ItemStack reagent1, ItemStack buffer1) implements RecipeInput {
    @Override
    public ItemStack getItem(int pIndex) {
        return switch (pIndex){
            case 0 -> this.base1;
            case 1 -> this.base2;
            case 2 -> this.base3;
            case 3 -> this.base4;
            case 4 -> this.base5;
            case 5 -> this.base6;
            case 6 -> this.base7;
            case 7 -> this.base8;
            case 8 -> this.base9;
            case 9 -> this.base10;
            case 10 -> this.reagent1;
            case 11 -> this.buffer1;
            default -> ItemStack.EMPTY;};
                                          }

    @Override
    public int size() {return 12;}
}
