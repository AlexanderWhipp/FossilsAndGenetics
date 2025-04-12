package net.shadowtek.fossilgencraft.block.entity.custom;

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
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.shadowtek.fossilgencraft.block.entity.ModBlockEntities;
import net.shadowtek.fossilgencraft.event.ModDataComponents;
import net.shadowtek.fossilgencraft.item.ModItems;
import net.shadowtek.fossilgencraft.screen.custom.GeneSplicerMenu;


import javax.annotation.Nullable;

public class SplicerBlockEntity extends BlockEntity implements MenuProvider {
    // Inventory with 4 slots: 0-2 are inputs, 3 is the output
    public final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged(); // Mark dirty when inventory changes
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);

            }
        }
    };
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;
    private static final int OUTPUT_SLOT = 3;

    public SplicerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GENE_SPLICER_COMPUTER_BE.get(), pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int p) {
                return switch (p) {
                    case 0 -> SplicerBlockEntity.this.progress;
                    case 1 -> SplicerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int p, int value) {
                switch (p) {
                    case 0 -> SplicerBlockEntity.this.progress = value;
                    case 1 -> SplicerBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    // Called every time crafting is attempted
    public void attemptCraft() {
        int totalGeneSize = 0;
        @Nullable String species = null;

        // Efficient input slotting: loop through 0-2 (input slots)
        for (int i = 0; i < OUTPUT_SLOT; i++) {
            ItemStack ingredientStack = itemHandler.getStackInSlot(i);
            if (!ingredientStack.isEmpty() && ingredientStack.is(ModItems.SEQUENCED_DNA_BASE_CHAIN.get())) {
                String speciesId = ingredientStack.getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unknown");
                System.out.println("Item Label: " + speciesId);

                Boolean contaminationStatus = ingredientStack.getOrDefault(ModDataComponents.IS_CONTAMINATED.get(), false);
                System.out.println("Contaminated: " + contaminationStatus);

                int geneSize = ingredientStack.getOrDefault(ModDataComponents.DNA_CHAIN_START_POS.get(), 10);
                System.out.println("GeneSize: " + geneSize);

                totalGeneSize += geneSize;

                if (species == null) {
                    // Consider adding validation if desired, e.g., !speciesId.equals("Unknown")
                    species = speciesId;
                }
                // No more 'else { return; }' - allows loop to continue

                // Accumulate gene size from ALL valid items
                // (Consider if contaminated items should add size? Add 'if (!contaminationStatus)' here if not)
                totalGeneSize += geneSize;

                // ---^^^ MINIMAL FIX ^^^---
            }
        }

        // Output stack creation
        ItemStack outputStack = new ItemStack(ModItems.NEAR_COMPLETE_GENOME.get());
        if (totalGeneSize == 30 && species != null && !itemHandler.getStackInSlot(0).isEmpty() && !itemHandler.getStackInSlot(1).isEmpty() && !itemHandler.getStackInSlot(3).isEmpty()) {
            outputStack.set(ModDataComponents.DNA_SPECIES_ID.get(), species);
            outputStack.set(ModDataComponents.DNA_CHAIN_START_POS.get(), totalGeneSize);
            itemHandler.setStackInSlot(OUTPUT_SLOT, outputStack);

            for (int i = 0; i < OUTPUT_SLOT; i++) {
                itemHandler.setStackInSlot(i, ItemStack.EMPTY);
            }

            setChanged();
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
        // pTag.putInt("dna_splicer.progress", progress);
        // pTag.putInt("dna_splicer.max_progress", maxProgress);
        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        // progress = pTag.getInt("dna_splicer.progress");
        // maxProgress = pTag.getInt("dna_splicer.max_progress");

    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.fossilgencraft.gene_splicing_computer");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new GeneSplicerMenu(pContainerId, pPlayerInventory, this, this.data);
    }


    public void tick(Level level, BlockPos blockpos, BlockState blockstate) {
        if (hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level, blockpos, blockstate);

            if (hasCraftingFinished()) {
                attemptCraft();
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

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;

    }

    private void increaseCraftingProgress() {
        progress++;
    }

    // Checks if item is craftable
    private boolean hasRecipe() {
        if (itemHandler.getStackInSlot(0).isEmpty()
                && itemHandler.getStackInSlot(1).isEmpty()
                && itemHandler.getStackInSlot(2).isEmpty()) {
            return false;
        }

        return itemHandler.getStackInSlot(3).isEmpty();
    }
}

