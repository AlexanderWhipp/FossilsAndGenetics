package net.shadowtek.fossilgencraft.block.entity.custom;

import cpw.mods.util.Lazy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.shadowtek.fossilgencraft.block.ModBlocks;
import net.shadowtek.fossilgencraft.block.entity.ModBlockEntities;
import net.shadowtek.fossilgencraft.item.ModItems;
import net.shadowtek.fossilgencraft.recipe.AmberExtractorRecipe;
import net.shadowtek.fossilgencraft.recipe.AmberExtractorRecipeInput;
import net.shadowtek.fossilgencraft.recipe.CentrifugeRecipe;
import net.shadowtek.fossilgencraft.recipe.ModRecipes;
import net.shadowtek.fossilgencraft.screen.custom.AmberExtractorMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.Optional;

public class AmberExtractorBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler inventory = new ItemStackHandler(8) { //set the number of slots
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1; //set the stack limit for each slot
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
            }
        }
    };
    //Input slot configuration
    private static final int INPUT_SLOT_1 = 1;
    private static final int INPUT_SLOT_2 = 2;
    private static final int INPUT_SLOT_3 = 3;
    private static final int INPUT_SLOT_4 = 4;
//Output slot configuration
    private static final int OUTPUT_SLOT_1 = 0;
    private static final int OUTPUT_SLOT_2 = 5;
    private static final int OUTPUT_SLOT_3 = 6;
    private static final int OUTPUT_SLOT_4 = 7;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;





    private float rotation;

    public AmberExtractorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.AMBER_EXTRACTOR_BE.get(), pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> AmberExtractorBlockEntity.this.progress;
                    case 1 -> AmberExtractorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0: AmberExtractorBlockEntity.this.progress = value;
                    case 1: AmberExtractorBlockEntity.this.maxProgress = value;
                }

            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> inventory);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        if(rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public void clearContents(){
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }
    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));  //searches the block inventory for items to drop.
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("inventory", inventory.serializeNBT(pRegistries));
        pTag.putInt("amber_extractor.progress",progress);
        pTag.putInt("amber_extractor.max_progress", maxProgress);//saves inventory and recipe progress when exiting game
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        inventory.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        pTag.getInt("amber_extractor.progress");
        pTag.getInt("amber_extractor.max_progress");//loads saved inventory on re-login
    }
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.fossilgencraft.amber_extractor_block"); //displays name of block in inventory
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AmberExtractorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public void tick(Level level, BlockPos blockpos, BlockState blockstate) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level, blockpos, blockstate);

            if (hasCraftingFinished()){
                craftItem();
                resetProgress();
            }

        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 72;
    }

    private void craftItem() {
        Optional<RecipeHolder<AmberExtractorRecipe>> recipe = getCurrentRecipe();
        ItemStack output_1 = recipe.get().value().output_1();
        ItemStack output_2 = recipe.get().value().output_2();
        ItemStack output_3 = recipe.get().value().output_3();
        ItemStack output_4 = recipe.get().value().output_4();

        inventory.extractItem(INPUT_SLOT_1, 1,false);
        inventory.extractItem(INPUT_SLOT_2,1,false);
        inventory.extractItem(INPUT_SLOT_3,1,false);
        inventory.extractItem(INPUT_SLOT_4,1,false);

        inventory.setStackInSlot(OUTPUT_SLOT_1, new ItemStack(output_1.getItem(),
                inventory.getStackInSlot(OUTPUT_SLOT_1).getCount() + output_1.getCount()));
        inventory.setStackInSlot(OUTPUT_SLOT_2, new ItemStack(output_2.getItem(),
                inventory.getStackInSlot(OUTPUT_SLOT_2).getCount() + output_2.getCount()));
        inventory.setStackInSlot(OUTPUT_SLOT_3, new ItemStack(output_3.getItem(),
                inventory.getStackInSlot(OUTPUT_SLOT_3).getCount() + output_3.getCount()));
        inventory.setStackInSlot(OUTPUT_SLOT_4, new ItemStack(output_4.getItem(),
                inventory.getStackInSlot(OUTPUT_SLOT_4).getCount() + output_4.getCount()));

        //V0.0.3 Hard code for extraction recipe - Replaced with custom recipe class. Safe to delete
        //  ItemStack output_1 = new ItemStack(ModItems.UNIDENTIFIED_DNA_SAMPLE.get()); //target output
       // ItemStack output_2 = new ItemStack(ModItems.AMBER_SHARD.get()); //junk item
       // ItemStack output_3 = new ItemStack(ModBlocks.AMBER_GEM_FLAWLESS.get()); //Misc crafting item
       // ItemStack output_4 = new ItemStack(ModItems.SYRINGE_CONTAMINATED.get());

       // inventory.extractItem(INPUT_SLOT_1, 1,false);
        //inventory.extractItem(INPUT_SLOT_2,1,false);
        //inventory.extractItem(INPUT_SLOT_3,1,false);
        //inventory.extractItem(INPUT_SLOT_4, 1,false);

       // inventory.setStackInSlot(OUTPUT_SLOT_1, new ItemStack(output_1.getItem(),
       //         inventory.getStackInSlot(OUTPUT_SLOT_1).getCount() + output_1.getCount()));
       // inventory.setStackInSlot(OUTPUT_SLOT_2, new ItemStack(output_2.getItem(),
       //         inventory.getStackInSlot(OUTPUT_SLOT_2).getCount() + output_2.getCount()));
       // inventory.setStackInSlot(OUTPUT_SLOT_3, new ItemStack(output_3.getItem(),
       //         inventory.getStackInSlot(OUTPUT_SLOT_3).getCount() + output_3.getCount()));
       // inventory.setStackInSlot(OUTPUT_SLOT_4, new ItemStack(output_4.getItem(),
       //         inventory.getStackInSlot(OUTPUT_SLOT_4).getCount() + output_4.getCount()));

    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<AmberExtractorRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()){
            return false;
        }
        ItemStack output_1 = recipe.get().value().output_1();
        ItemStack output_2 = recipe.get().value().output_2();
        ItemStack output_3 = recipe.get().value().output_3();
        ItemStack output_4 = recipe.get().value().output_4();

        return canInsertItemIntoOutputSlot(output_1,output_2,output_3,output_4)
                && canInsertAmountIntoOutputSlot(output_1.getCount(),output_2.getCount(),output_3.getCount(),output_4.getCount());
        // V0.0.3 old hard code for recipe - kept for reference purposes
      //  Block input1 = ModBlocks.AMBER_GEM_INSECT.get();
     //   Item input_2 = ModItems.SYRINGE_EMPTY.get();
     //   Item input_3 = ModItems.TEST_TUBE_WATER.get();
      //  Item input_4 = Items.COAL;

      //  ItemStack output_1 = new ItemStack(ModItems.UNIDENTIFIED_DNA_SAMPLE.get()); //target output
      //  ItemStack output_2 = new ItemStack(ModItems.AMBER_SHARD.get()); //junk item
     //   ItemStack output_3 = new ItemStack(ModBlocks.AMBER_GEM_FLAWLESS.get()); //Misc crafting item
      //  ItemStack output_4 = new ItemStack(ModItems.SYRINGE_CONTAMINATED.get()); //Crafting Ingrediant Vital

    //    return inventory.getStackInSlot(INPUT_SLOT_1).is(input1.asItem()) &&
     //   inventory.getStackInSlot(INPUT_SLOT_2).is(input_2) &&
     //   inventory.getStackInSlot(INPUT_SLOT_3).is(input_3) &&
     //   inventory.getStackInSlot(INPUT_SLOT_4).is(input_4) && canInsertItemIntoOutputSlot(output_1,output_2,output_3, output_4) &&
        //        canInsertAmountIntoOutputSlot(output_1.getCount()) && canInsertAmountIntoOutputSlot(output_2.getCount()) &&
         //       canInsertAmountIntoOutputSlot(output_3.getCount()) && canInsertAmountIntoOutputSlot(output_4.getCount());

    }
    private Optional <RecipeHolder<AmberExtractorRecipe>> getCurrentRecipe(){
        return this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.AMBER_EXTRACTOR_TYPE.get(), new AmberExtractorRecipeInput(inventory.getStackInSlot(INPUT_SLOT_1),inventory.getStackInSlot(INPUT_SLOT_2),inventory.getStackInSlot(INPUT_SLOT_3),inventory.getStackInSlot(INPUT_SLOT_4)), level);
    }
    private boolean canInsertItemIntoOutputSlot(ItemStack output_1,ItemStack output_2, ItemStack output_3, ItemStack output_4){
        return inventory.getStackInSlot(OUTPUT_SLOT_1).isEmpty() || this.inventory.getStackInSlot(OUTPUT_SLOT_1).getItem() == output_1.getItem() &&
                inventory.getStackInSlot(OUTPUT_SLOT_2).isEmpty()|| this.inventory.getStackInSlot(OUTPUT_SLOT_2).getItem() == output_2.getItem() &&
                inventory.getStackInSlot(OUTPUT_SLOT_3).isEmpty()|| this.inventory.getStackInSlot(OUTPUT_SLOT_3).getItem() == output_3.getItem() &&
                inventory.getStackInSlot(OUTPUT_SLOT_4).isEmpty()|| this.inventory.getStackInSlot(OUTPUT_SLOT_4).getItem() == output_4.getItem() ;

    }
    private boolean canInsertAmountIntoOutputSlot(int count1, int count2, int count3, int count4){
        int maxCount1 = inventory.getStackInSlot(OUTPUT_SLOT_1).isEmpty() ? 1 : inventory.getStackInSlot(OUTPUT_SLOT_1).getMaxStackSize();
        int currentCount1 = inventory.getStackInSlot(OUTPUT_SLOT_1).getCount();
        int maxCount2 = inventory.getStackInSlot(OUTPUT_SLOT_2).isEmpty() ? 64 : inventory.getStackInSlot(OUTPUT_SLOT_2).getMaxStackSize();
        int currentCount2 = inventory.getStackInSlot(OUTPUT_SLOT_2).getCount();
        int maxCount3 = inventory.getStackInSlot(OUTPUT_SLOT_3).isEmpty() ? 64 : inventory.getStackInSlot(OUTPUT_SLOT_3).getMaxStackSize();
        int currentCount3 = inventory.getStackInSlot(OUTPUT_SLOT_3).getCount();
        int maxCount4 = inventory.getStackInSlot(OUTPUT_SLOT_4).isEmpty() ? 16 : inventory.getStackInSlot(OUTPUT_SLOT_3).getMaxStackSize();
        int currentCount4 = inventory.getStackInSlot(OUTPUT_SLOT_4).getCount();

        return maxCount1 >= currentCount1 + count1 &&
                maxCount2 >= currentCount2 + count2 &&
                maxCount3 >= currentCount3 + count3 &&
                maxCount4 >= currentCount4 + count4;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this); //required for client -> server synchronisation
    }
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }


}
