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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.shadowtek.fossilgencraft.block.entity.ModBlockEntities;
import net.shadowtek.fossilgencraft.recipe.*;
import net.shadowtek.fossilgencraft.screen.custom.CentrifugeMenu;
import net.shadowtek.fossilgencraft.screen.custom.FreezeDryerMenu;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FreezeDryerBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;



    public FreezeDryerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FREEZEDRYER_BE.get(), pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int i){
                return switch (i){
                    case 0 -> FreezeDryerBlockEntity.this.progress;
                    case 1 -> FreezeDryerBlockEntity.this.maxProgress;
                    default -> 0;
                };

            }
            @Override
            public void set(int i, int value){
                switch (i) {
                    case 0:
                        FreezeDryerBlockEntity.this.progress = value;
                    case 1:
                        FreezeDryerBlockEntity.this.maxProgress = value;
                }
            }
            @Override public int getCount(){
                return 2;
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(()  -> itemHandler);
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
        pTag.putInt("centrifuge.progress", progress);
        pTag.putInt("centrifuge.max_progress", maxProgress);

        super.saveAdditional(pTag, pRegistries);
    }


    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        progress = pTag.getInt("freezedryer.progress");
        maxProgress = pTag.getInt("freezedryer.max_progress");

    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.fossilgencraft.freezedryer");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FreezeDryerMenu(pContainerId, pPlayerInventory, this, this.data);
    }
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level, blockPos, blockState);

            if (hasCraftingFinished()) {
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
        Optional<RecipeHolder<FreezeDryerRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().output();

        itemHandler.extractItem(INPUT_SLOT, 1, false);
        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }
    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }
    private void increaseCraftingProgress() {
        progress++;
    }
    private boolean hasRecipe() {
        Optional<RecipeHolder<FreezeDryerRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack output = recipe.get().value().output();
        return canInsertItemIntoOutputSlot(output) && canInsertAmountIntoOutputSlot(output.getCount());
    }
    private Optional<RecipeHolder<FreezeDryerRecipe>> getCurrentRecipe() {
        return this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.FREEZEDRYER_TYPE.get(), new FreezeDryerRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT)), level);
    }
    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Override
    @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}

