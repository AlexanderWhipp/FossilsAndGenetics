package net.shadowtek.fossilgencraft.screen.custom.supercomputer;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import net.shadowtek.fossilgencraft.block.ModBlocks;
import net.shadowtek.fossilgencraft.block.entity.custom.supercomputer.SuperComputerTerminalBlockEntity;
import net.shadowtek.fossilgencraft.screen.ModMenuTypes;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.Platform;

public class SuperComputerMenu extends AbstractContainerMenu {

    public final SuperComputerTerminalBlockEntity blockEntity;
    protected final Player player;
    private final ContainerLevelAccess levelAccess;
    // ContainerData used to sync integers like progress between BE and Screen
    // Ensure BE's dataAccess provides correct values for index 0 (progress) and 1 (maxProgress)
    protected final ContainerData data;

    public SuperComputerMenu(int pContainerId, Inventory pPlayerInventory, SuperComputerTerminalBlockEntity pBlockEntity, ContainerData pData) {
        super(ModMenuTypes.SUPERCOMPUTER_MENU.get(), pContainerId);
        this.blockEntity = pBlockEntity;
        this.player = pPlayerInventory.player;
        this.levelAccess = ContainerLevelAccess.create(pBlockEntity.getLevel(), pBlockEntity.getBlockPos());
        checkContainerDataCount(pData, 2); // We expect 2 ints (progress, maxProgress)
        this.data = pData;

        // --- IMPORTANT: No Player Inventory Slots Added ---
        // As requested, this GUI is custom and doesn't show player inv slots.

        // --- Add Slots for Supercomputer's INTERNAL inventory (e.g., Hard Drive Slot) ---
        // Even if GUI is custom, Menu needs slots if items can be inserted/extracted via automation/shift-click later
        // Example: Add the Hard Drive Slot (index 0 in BE's internal handler)
        // Adjust position (80, 35) as needed
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 0, 80, 35));
        // TODO: Add slots for Energy input / Process output if needed

        // --- Add ContainerData tracking ---
        addDataSlots(pData);

    }

    public SuperComputerMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        // Create dummy BE reference/data on client, real data comes from sync/packets
        // Need to read BlockPos from buffer to find the BE on the client world
        this(pContainerId, pPlayerInventory, getClientBlockEntity(pPlayerInventory, extraData), new SimpleContainerData(1)); // Use SimpleContainerData for client sync
    }

    private static SuperComputerTerminalBlockEntity getClientBlockEntity(Inventory playerInventory, FriendlyByteBuf buf) {
        final BlockEntity be = playerInventory.player.level().getBlockEntity(buf.readBlockPos());
        if (be instanceof SuperComputerTerminalBlockEntity sbe) {
            return sbe;
        }
        throw new IllegalStateException("Incorrect BlockEntity type received on client! Expected SupercomputerBlockEntity at saved pos.");
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.levelAccess, pPlayer, ModBlocks.SUPER_COMPUTER_TERMINAL.get()); // <<< Use your actual Block
    }


    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 1;  // must be the number of slots you have!

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    public SuperComputerTerminalBlockEntity getBlockEntity() {
        return this.blockEntity;
    }
    public int getProgress() { return this.data.get(0); }
    public int getMaxProgress() { return this.data.get(1); }
    public boolean isCrafting() { return getProgress() > 0; } // Helper






}