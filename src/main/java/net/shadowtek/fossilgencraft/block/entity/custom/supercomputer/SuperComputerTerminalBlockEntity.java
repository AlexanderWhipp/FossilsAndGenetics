package net.shadowtek.fossilgencraft.block.entity.custom.supercomputer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.block.entity.ModBlockEntities;
import net.shadowtek.fossilgencraft.item.ModItems;
import net.shadowtek.fossilgencraft.screen.custom.supercomputer.SuperComputerMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SuperComputerTerminalBlockEntity extends BlockEntity implements MenuProvider{
    public final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide() && slot == 0) {
                resetProgress();
            }
        }


        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return slot == 0 && stack.is(ModItems.SUPER_COMPUTER_HARD_DRIVE.get()); // <<< Use your actual Hard Drive Item
        }
    };
    private static final int HARD_DRIVE_BAY = 0;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private int maxProgress;
    private int progress;
    private static Level plevel;
    public final ContainerData dataAccess = new ContainerData() {
        @Override public int get(int index) {
            return switch (index) {
                case 0 -> SuperComputerTerminalBlockEntity.this.progress;
                case 1 -> SuperComputerTerminalBlockEntity.this.maxProgress;
                default -> 0;
            };
        }
        @Override public void set(int index, int value) {
            switch (index) {
                case 0 -> SuperComputerTerminalBlockEntity.this.progress = value; // Client syncs progress for display
                case 1 -> SuperComputerTerminalBlockEntity.this.maxProgress = value; // Client syncs max progress for display
            }
        }
        @Override public int getCount() { return 2; /* Number of integers being synced */ }
    };


    public SuperComputerTerminalBlockEntity(BlockPos pPos, BlockState pBlockstate) {
        super(ModBlockEntities.SUPER_COMPUTER_TERMINAL_BE.get(),pPos, pBlockstate);

    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
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
    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getStackInSlot(0));
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
      return Component.translatable("block.fossilgencraft.supercomputerterminal");
    }


    // Instance tick method containing the core logic
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
    }



    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 0; // Reset maxProgress so isResearchActiveAndCanProgress starts failing
        // Don't necessarily need setChanged() here, as the calling methods handle it.
        FossilGenCraft.LOGGER.debug("Resetting research progress.");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putInt("research.progress", this.progress);
        pTag.putInt("research.maxProgress", this.maxProgress);
        pTag.put("inventory", itemHandler.serializeNBT(pRegistries)); // Save inventory too
        // Save energy if needed
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.progress = pTag.getInt("research.progress");
        this.maxProgress = pTag.getInt("research.maxProgress");
        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory")); // Load inventory
        // Load energy if needed
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider holders) {
        super.handleUpdateTag(tag, holders);
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        // Send essential data needed by menu/screen immediately upon opening?
        // For now, rely on ContainerData and initial UpdateTag sync
        return new SuperComputerMenu(pContainerId, pPlayerInventory, this, this.dataAccess); // Pass BE and ContainerData
    }

}
