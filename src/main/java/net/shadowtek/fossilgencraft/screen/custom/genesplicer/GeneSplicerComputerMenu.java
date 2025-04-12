package net.shadowtek.fossilgencraft.screen.custom.genesplicer;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import net.shadowtek.fossilgencraft.block.ModBlocks;
import net.shadowtek.fossilgencraft.block.entity.custom.FreezeDryerBlockEntity;
import net.shadowtek.fossilgencraft.block.entity.custom.GeneSplicerComputerBlockEntity;
import net.shadowtek.fossilgencraft.screen.ModMenuTypes;
import net.shadowtek.fossilgencraft.util.CoverageValidator;

public class GeneSplicerComputerMenu extends AbstractContainerMenu {
    public final GeneSplicerComputerBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public GeneSplicerComputerMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(32));
    }

    public GeneSplicerComputerMenu(int pContainerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.GENE_SPLICER_MENU.get(), pContainerId);
        this.blockEntity = (GeneSplicerComputerBlockEntity) blockEntity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
//will clean this up later, I just want this damn block to work
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 0,-35,-6));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 1,-17,-6));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 2,1,-6));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 3,19,-6));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 4,37,-6));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 5,55,-6));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 6,-35,12));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 7,-17,12));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 8,1,12));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 9,19,12));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 10,37,12));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 11,55,12));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 12,-35,30));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 13,-17,30));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 14,1,30));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 15,19,30));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 16,37,30));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 17,55,30));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 18,-35,48));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 19,-17,48));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 20,1,48));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 21,19,48));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 22,37,48));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 23,55,48));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 24,123,-6));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 25,141,-6));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 26,159,-6));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 27,177,-6));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 28,195,-6));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 29,213,-6));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 30,196,119));
        this.addSlot(new SlotItemHandler(this.blockEntity.inventory, 31,214,119));




    }
    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public int getScaledArrowProgress(){
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowPixelSize = 24;

        return maxProgress != 0 && progress !=0 ? progress * arrowPixelSize / maxProgress : 0;
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
    private static final int TE_INVENTORY_SLOT_COUNT = 32;  // must be the number of slots you have!
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





        @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.GENE_SPLICER_COMPUTER.get());
    }


    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 10 + l * 18, 115 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 30 + i * 18, 210));
        }
    }

}
