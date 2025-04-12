package net.shadowtek.fossilgencraft.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.shadowtek.fossilgencraft.block.entity.ModBlockEntities;
import net.shadowtek.fossilgencraft.event.ModDataComponents;
import net.shadowtek.fossilgencraft.item.ModItems;
import net.shadowtek.fossilgencraft.recipe.AmberExtractorRecipe;
import net.shadowtek.fossilgencraft.recipe.AmberExtractorRecipeInput;
import net.shadowtek.fossilgencraft.recipe.ModRecipes;
import net.shadowtek.fossilgencraft.screen.custom.AmberExtractorMenu;
import net.shadowtek.fossilgencraft.screen.custom.genesplicer.GeneSplicerComputerMenu;
import org.apache.commons.lang3.IntegerRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.minecraft.world.level.storage.loot.IntRange.range;

public class GeneSplicerComputerBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler inventory = new ItemStackHandler(32) { //set the number of slots
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
    private int startPos = 0;

    
    //DNA CHAIN INPUT
    private static final int DNA_SLOT_1 = 0;
    private static final int DNA_SLOT_2 = 1;
    private static final int DNA_SLOT_3 = 2;
    private static final int DNA_SLOT_4 = 3;
    private static final int DNA_SLOT_5 = 4;
    private static final int DNA_SLOT_6 = 5;
    private static final int DNA_SLOT_7 = 6;
    private static final int DNA_SLOT_8 = 7;
    private static final int DNA_SLOT_9 = 8;
    private static final int DNA_SLOT_10 = 9;
    private static final int DNA_SLOT_11 = 10;
    private static final int DNA_SLOT_12 = 11;
    private static final int DNA_SLOT_13 = 12;
    private static final int DNA_SLOT_14 = 13;
    private static final int DNA_SLOT_15 = 14;
    private static final int DNA_SLOT_16 = 15;
    private static final int DNA_SLOT_17 = 16;
    private static final int DNA_SLOT_18 = 17;
    private static final int DNA_SLOT_19 = 18;
    private static final int DNA_SLOT_20 = 19;
    private static final int DNA_SLOT_21 = 20;
    private static final int DNA_SLOT_22 = 21;
    private static final int DNA_SLOT_23 = 22;
    private static final int DNA_SLOT_24 = 23;



    //Filler DNA Slots

    private static final int FILLER_SLOT_1 = 24;
    private static final int FILLER_SLOT_2 = 25;
    private static final int FILLER_SLOT_3 = 26;
    private static final int FILLER_SLOT_4 = 27;
    private static final int FILLER_SLOT_5 = 28;
    private static final int FILLER_SLOT_6 = 29;

    //Output slot configuration
    private static final int OUTPUT_SLOT_1 = 30;
    private static final int OUTPUT_SLOT_2 = 31;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private int chainStartPos = 1;
    private int chainEndPos = 100;





    public GeneSplicerComputerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GENE_SPLICER_COMPUTER_BE.get(), pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> GeneSplicerComputerBlockEntity.this.progress;
                    case 1 -> GeneSplicerComputerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0: GeneSplicerComputerBlockEntity.this.progress = value;
                    case 1: GeneSplicerComputerBlockEntity.this.maxProgress = value;
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
        pTag.putInt("gene_splicer_computer.progress",progress);
        pTag.putInt("gene_splicer_computer.max_progress", maxProgress);//saves inventory and recipe progress when exiting game
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        inventory.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        pTag.getInt("gene_splicer_computer.progress");
        pTag.getInt("gene_splicer_computer.max_progress");//loads saved inventory on re-login
    }
    @Override
    public Component getDisplayName() {
        return Component.translatable("block.fossilgencraft.gene_splicer_computer_block"); //displays name of block in inventory
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new GeneSplicerComputerMenu(pContainerId, pPlayerInventory, this, this.data);
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
   public void hasRecipe(){
       ItemStack input_1 = inventory.getStackInSlot(DNA_SLOT_1);
       System.err.println("Item In Slot 1: " + input_1);

       //if (item in DnaSlot1 contains Datacomponent.species) {
//                      currentSpecies = Item Datacomponent.species
//          } else {
   }

   private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 72;
    }

    private void craftItem() {
        inventory.extractItem(DNA_SLOT_1, 1, false);
        inventory.extractItem(DNA_SLOT_2, 1, false);
        inventory.extractItem(DNA_SLOT_3, 1, false);
        inventory.extractItem(DNA_SLOT_4, 1, false);
        inventory.extractItem(DNA_SLOT_5, 1, false);
        inventory.extractItem(DNA_SLOT_6, 1, false);
        inventory.extractItem(DNA_SLOT_7, 1, false);
        inventory.extractItem(DNA_SLOT_8, 1, false);
        inventory.extractItem(DNA_SLOT_9, 1, false);
        inventory.extractItem(DNA_SLOT_10, 1, false);
        inventory.extractItem(DNA_SLOT_11, 1, false);
        inventory.extractItem(DNA_SLOT_12, 1, false);
        inventory.extractItem(DNA_SLOT_13, 1, false);
        inventory.extractItem(DNA_SLOT_14, 1, false);
        inventory.extractItem(DNA_SLOT_15, 1, false);
        inventory.extractItem(DNA_SLOT_16, 1, false);
        inventory.extractItem(DNA_SLOT_17, 1, false);
        inventory.extractItem(DNA_SLOT_18, 1, false);
        inventory.extractItem(DNA_SLOT_19, 1, false);
        inventory.extractItem(DNA_SLOT_20, 1, false);
        inventory.extractItem(DNA_SLOT_21, 1, false);
        inventory.extractItem(DNA_SLOT_22, 1, false);
        inventory.extractItem(DNA_SLOT_23, 1, false);
        inventory.extractItem(DNA_SLOT_24, 1, false);
        inventory.extractItem(FILLER_SLOT_1, 1, false);
        inventory.extractItem(FILLER_SLOT_2, 1, false);
        inventory.extractItem(FILLER_SLOT_3, 1, false);
        inventory.extractItem(FILLER_SLOT_4, 1, false);
        inventory.extractItem(FILLER_SLOT_5, 1, false);
        inventory.extractItem(FILLER_SLOT_6, 1, false);

    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output_1,ItemStack output_2){
        return inventory.getStackInSlot(OUTPUT_SLOT_1).isEmpty() || this.inventory.getStackInSlot(OUTPUT_SLOT_1).getItem() == output_1.getItem() &&
                inventory.getStackInSlot(OUTPUT_SLOT_2).isEmpty()|| this.inventory.getStackInSlot(OUTPUT_SLOT_2).getItem() == output_2.getItem() ;

    }
    private boolean canInsertAmountIntoOutputSlot(int count1, int count2){
        int maxCount1 = inventory.getStackInSlot(OUTPUT_SLOT_1).isEmpty() ? 1 : inventory.getStackInSlot(OUTPUT_SLOT_1).getMaxStackSize();
        int currentCount1 = inventory.getStackInSlot(OUTPUT_SLOT_1).getCount();
        int maxCount2 = inventory.getStackInSlot(OUTPUT_SLOT_2).isEmpty() ? 64 : inventory.getStackInSlot(OUTPUT_SLOT_2).getMaxStackSize();
        int currentCount2 = inventory.getStackInSlot(OUTPUT_SLOT_2).getCount();


        return maxCount1 >= currentCount1 + count1 &&
                maxCount2 >= currentCount2 + count2;
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


