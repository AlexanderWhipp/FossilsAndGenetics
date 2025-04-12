package net.shadowtek.fossilgencraft.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.datafix.fixes.ItemStackComponentizationFix;
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
import net.shadowtek.fossilgencraft.recipe.CentrifugeRecipe;
import net.shadowtek.fossilgencraft.recipe.CentrifugeRecipeInput;
import net.shadowtek.fossilgencraft.recipe.ModRecipes;
import net.shadowtek.fossilgencraft.screen.custom.CentrifugeMenu;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CentrifugeBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private static final int INPUT_SLOT1 = 0;
    private static final int INPUT_SLOT2 = 2;
    private static final int OUTPUT_SLOT1 = 1;
    private static final int OUTPUT_SLOT2 = 3;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;



    public CentrifugeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CENTRIFUGE_BE.get(), pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int i){
                return switch (i){
                    case 0 -> CentrifugeBlockEntity.this.progress;
                    case 1 -> CentrifugeBlockEntity.this.maxProgress;
                    default -> 0;
                };

            }
            @Override
            public void set(int i, int value){
                switch (i) {
                    case 0:
                        CentrifugeBlockEntity.this.progress = value;
                    case 1:
                        CentrifugeBlockEntity.this.maxProgress = value;
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
            progress = pTag.getInt("centrifuge.progress");
            maxProgress = pTag.getInt("centrifuge.max_progress");

    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.fossilgencraft.centrifuge");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new CentrifugeMenu(pContainerId, pPlayerInventory, this, this.data);
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
        Optional<RecipeHolder<CentrifugeRecipe>> recipe = getCurrentRecipe();
        ItemStack output1 = recipe.get().value().output1();
        ItemStack output2 = recipe.get().value().output2();

        itemHandler.extractItem(INPUT_SLOT1, 1, false);
        itemHandler.extractItem(INPUT_SLOT2, 1,false);

        itemHandler.setStackInSlot(OUTPUT_SLOT1, new ItemStack(output1.getItem(),
                itemHandler.getStackInSlot(OUTPUT_SLOT1).getCount() + output1.getCount()));
        itemHandler.setStackInSlot(OUTPUT_SLOT2, new ItemStack(output2.getItem()));
    }
    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }
    private void increaseCraftingProgress() {
        progress++;
    }
    private boolean hasRecipe() {
        Optional<RecipeHolder<CentrifugeRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack output1 = recipe.get().value().output1();
        ItemStack output2 = recipe.get().value().output2();

        return canInsertItemIntoOutputSlot(output1, output2) && canInsertAmountIntoOutputSlot(output1.getCount(),output2.getCount());
    }
    private Optional<RecipeHolder<CentrifugeRecipe>> getCurrentRecipe() {
        return this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.CENTRIFUGE_TYPE.get(), new CentrifugeRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT1),itemHandler.getStackInSlot(INPUT_SLOT2)), level);
    }
    private boolean canInsertItemIntoOutputSlot(ItemStack output1, ItemStack output2) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT1).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT1).getItem() == output1.getItem()&&
                itemHandler.getStackInSlot(OUTPUT_SLOT2).isEmpty()|| this.itemHandler.getStackInSlot(OUTPUT_SLOT2).getItem() == output2.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count1, int count2) {
        int maxCount1 = itemHandler.getStackInSlot(OUTPUT_SLOT1).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT1).getMaxStackSize();
        int currentCount1 = itemHandler.getStackInSlot(OUTPUT_SLOT1).getCount();
        int maxCount2 = itemHandler.getStackInSlot(OUTPUT_SLOT2).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT2).getMaxStackSize();
        int currentCount2 = itemHandler.getStackInSlot(OUTPUT_SLOT2).getCount();

        return maxCount1 >= currentCount1 + count1 &&
                maxCount2 >= currentCount2 + count2;
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