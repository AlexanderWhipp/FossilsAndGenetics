package net.shadowtek.fossilgencraft.block.entity.custom;

import net.minecraft.client.Minecraft;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.unsafe.UnsafeFieldAccess;
import net.shadowtek.fossilgencraft.block.entity.ModBlockEntities;
import net.shadowtek.fossilgencraft.event.ModDataComponents;
import net.shadowtek.fossilgencraft.item.ModItems;
import net.shadowtek.fossilgencraft.recipe.CentrifugeRecipe;
import net.shadowtek.fossilgencraft.recipe.CentrifugeRecipeInput;
import net.shadowtek.fossilgencraft.recipe.DnaSplittingCentrifuge;
import net.shadowtek.fossilgencraft.recipe.ModRecipes;
import net.shadowtek.fossilgencraft.screen.custom.CentrifugeMenu;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CentrifugeBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(12) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private static final int INPUT_SLOT1 = 0;
    private static final int INPUT_SLOT2 = 1;
    private static final int OUTPUT_SLOT1 = 2;
    private static final int OUTPUT_SLOT2 = 3;
    private static final int OUTPUT_SLOT3 = 4;
    private static final int OUTPUT_SLOT4 = 5;
    private static final int OUTPUT_SLOT5 = 6;
    private static final int OUTPUT_SLOT6 = 7;
    private static final int OUTPUT_SLOT7 = 8;
    private static final int OUTPUT_SLOT8 = 9;
    private static final int OUTPUT_SLOT9 = 10;
    private static final int OUTPUT_SLOT10 = 11;

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
        if(hasRecipe()||hasSplittingRecipe()) {
            increaseCraftingProgress();
            setChanged(level, blockPos, blockState);

            if (hasCraftingFinished()) {
                if(itemHandler.getStackInSlot(INPUT_SLOT1).is(ModItems.CONTAMINATED_MOSQUITO_SAMPLE.get())){
                craftItem();
                resetProgress();
            } else if (itemHandler.getStackInSlot(INPUT_SLOT1).is(ModItems.COMPLETED_GENOME.get()) && itemHandler.getStackInSlot(INPUT_SLOT2).is(ModItems.CHEMICAL_LYSIS_BUFFER.get())){
                    craftGenes();
                    resetProgress();
                } else if (itemHandler.getStackInSlot(INPUT_SLOT1).is(ModItems.COMPLETED_GENOME.get()) && itemHandler.getStackInSlot(INPUT_SLOT2).is(Items.EGG)){
                    craftEgg();
                    resetProgress();
                }
            }
        } else {
            resetProgress();
        }
    }
    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 72;
    }
    private void craftEgg(){
        Optional<RecipeHolder<CentrifugeRecipe>> recipe = getCurrentRecipe();
        ItemStack output1 = recipe.get().value().output1();
        ItemStack output2 = recipe.get().value().output2();

        String geneSetterId = itemHandler.getStackInSlot(INPUT_SLOT1).get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_1.get());
        String gene2SetterId = itemHandler.getStackInSlot(INPUT_SLOT1).get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_2.get());
        String gene3SetterId = itemHandler.getStackInSlot(INPUT_SLOT1).get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_3.get());
        ItemStack egg = new ItemStack(ModItems.CUSTOM_SPAWN_EGG.get(), output1.getCount());

        itemHandler.extractItem(INPUT_SLOT1, 1, false);
        itemHandler.extractItem(INPUT_SLOT2,1,false);

        egg.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_1.get(),geneSetterId);
        egg.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_2.get(),gene2SetterId);
        egg.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_3.get(),gene3SetterId);


        ItemStack existingOutput1 = this.itemHandler.getStackInSlot(OUTPUT_SLOT1);
        if(existingOutput1.isEmpty()) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT1, egg);
        } else {
            existingOutput1.grow(egg.getCount());
        }
        ItemStack existingOutput2 = this.itemHandler.getStackInSlot(OUTPUT_SLOT2);
        if(existingOutput2.isEmpty()) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT2, output2);
        } else {
            existingOutput2.grow(output2.getCount());
        }


    }
    private void craftGenes(){
        Optional<RecipeHolder<DnaSplittingCentrifuge>> recipe = getGeneRecipe();
        ItemStack output1 = recipe.get().value().gene1();
        ItemStack output2 = recipe.get().value().gene2();
        ItemStack output3 = recipe.get().value().gene3();
        ItemStack output4 = recipe.get().value().gene4();
        ItemStack output5 = recipe.get().value().gene5();
        ItemStack output6 = recipe.get().value().gene6();
        ItemStack output7 = recipe.get().value().gene7();    ;
        ItemStack output8 = recipe.get().value().gene8();
        ItemStack output9 = recipe.get().value().gene9();
        ItemStack output10 = recipe.get().value().gene10();


        String speciesId = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unreadable Species");

        String speciesId1 = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_1.get(), speciesId);
        String speciesId2 = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_2.get(), speciesId);
        String speciesId3 = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_3.get(), speciesId);
        String speciesId4 = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_4.get(), speciesId);
        String speciesId5 = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_5.get(), speciesId);
        String speciesId6 = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_6.get(), speciesId);
        String speciesId7 = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_7.get(), speciesId);
        String speciesId8 = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_8.get(), speciesId);
        String speciesId9 = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_9.get(), speciesId);
        String speciesId10 = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_10.get(), speciesId);


        String fullGeneCode = itemHandler.getStackInSlot(INPUT_SLOT1).get(ModDataComponents.DNA_FULL_GENOME_CODE.get());


        Integer contamination = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(), 0);
        Integer contamPerGene;
        if (contamination != 0){
            contamPerGene = contamination/10;
        } else {
            contamPerGene = 0;
        }

        Float integrity = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 0.00f);
        Float integPerGene;
        if (integrity!=0.00f) {
            integPerGene = integrity / 10f;
        } else {
            integPerGene = 0f;
        }

        itemHandler.extractItem(INPUT_SLOT1, 1, false);
        itemHandler.extractItem(INPUT_SLOT2,1,false);

            final int geneCodeLength = 16;
            List<String> geneCodeParts = new ArrayList<>();

            for (int  i = 0; i <fullGeneCode.length(); i +=geneCodeLength) {
                geneCodeParts.add(fullGeneCode.substring(i, i + geneCodeLength));
            }

            String geneCode1 = geneCodeParts.get(0);
            String geneCode2 = geneCodeParts.get(1);
            String geneCode3 = geneCodeParts.get(2);
            String geneCode4 = geneCodeParts.get(3);
            String geneCode5 = geneCodeParts.get(4);
            String geneCode6 = geneCodeParts.get(5);
            String geneCode7 = geneCodeParts.get(6);
            String geneCode8 = geneCodeParts.get(7);
            String geneCode9 = geneCodeParts.get(8);
            String geneCode10 = geneCodeParts.get(9);




            ItemStack gene1 = new ItemStack(ModItems.SEQUENCED_DNA_CHAIN.get(), output1.getCount());
            ItemStack gene2 = new ItemStack(ModItems.SEQUENCED_DNA_CHAIN.get(), output2.getCount());
            ItemStack gene3 = new ItemStack(ModItems.SEQUENCED_DNA_CHAIN.get(), output3.getCount());
            ItemStack gene4 = new ItemStack(ModItems.SEQUENCED_DNA_CHAIN.get(), output4.getCount());
            ItemStack gene5 = new ItemStack(ModItems.SEQUENCED_DNA_CHAIN.get(), output5.getCount());
            ItemStack gene6 = new ItemStack(ModItems.SEQUENCED_DNA_CHAIN.get(), output6.getCount());
            ItemStack gene7 = new ItemStack(ModItems.SEQUENCED_DNA_CHAIN.get(), output7.getCount());
            ItemStack gene8 = new ItemStack(ModItems.SEQUENCED_DNA_CHAIN.get(), output8.getCount());
            ItemStack gene9 = new ItemStack(ModItems.SEQUENCED_DNA_CHAIN.get(), output9.getCount());
            ItemStack gene10 = new ItemStack(ModItems.SEQUENCED_DNA_CHAIN.get(), output10.getCount());



            gene1.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesId1);
            gene1.set(ModDataComponents.DNA_GENE_CODE.get(), geneCode1);
            gene1.set(ModDataComponents.DNA_ERA_ID.get(), "modern");
            gene1.set(ModDataComponents.DNA_SLOT_NO.get(), 1);
            gene1.set(ModDataComponents.CONTAMINATED_SCORE.get(), contamPerGene);
            gene1.set(ModDataComponents.DNA_INTEGRITY_ID.get(), integPerGene);


            gene2.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesId2);
            gene2.set(ModDataComponents.DNA_GENE_CODE.get(), geneCode2);
            gene2.set(ModDataComponents.DNA_ERA_ID.get(), "modern");
            gene2.set(ModDataComponents.DNA_SLOT_NO.get(), 2);
            gene2.set(ModDataComponents.CONTAMINATED_SCORE.get(), contamPerGene);
            gene2.set(ModDataComponents.DNA_INTEGRITY_ID.get(), integPerGene);

            gene3.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesId3);
            gene3.set(ModDataComponents.DNA_GENE_CODE.get(), geneCode3);
            gene3.set(ModDataComponents.DNA_ERA_ID.get(), "modern");
            gene3.set(ModDataComponents.DNA_SLOT_NO.get(), 3);
            gene3.set(ModDataComponents.CONTAMINATED_SCORE.get(), contamPerGene);
            gene3.set(ModDataComponents.DNA_INTEGRITY_ID.get(), integPerGene);

            gene4.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesId4);
            gene4.set(ModDataComponents.DNA_GENE_CODE.get(), geneCode4);
            gene4.set(ModDataComponents.DNA_ERA_ID.get(), "modern");
            gene4.set(ModDataComponents.DNA_SLOT_NO.get(), 4);
            gene4.set(ModDataComponents.CONTAMINATED_SCORE.get(), contamPerGene);
            gene4.set(ModDataComponents.DNA_INTEGRITY_ID.get(), integPerGene);

            gene5.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesId5);
            gene5.set(ModDataComponents.DNA_GENE_CODE.get(), geneCode5);
            gene5.set(ModDataComponents.DNA_ERA_ID.get(), "modern");
            gene5.set(ModDataComponents.DNA_SLOT_NO.get(), 5);
            gene5.set(ModDataComponents.CONTAMINATED_SCORE.get(), contamPerGene);
            gene5.set(ModDataComponents.DNA_INTEGRITY_ID.get(), integPerGene);

            gene6.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesId6);
            gene6.set(ModDataComponents.DNA_GENE_CODE.get(), geneCode6);
            gene6.set(ModDataComponents.DNA_ERA_ID.get(), "modern");
            gene6.set(ModDataComponents.DNA_SLOT_NO.get(), 6);
            gene6.set(ModDataComponents.CONTAMINATED_SCORE.get(), contamPerGene);
            gene6.set(ModDataComponents.DNA_INTEGRITY_ID.get(), integPerGene);

            gene7.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesId7);
            gene7.set(ModDataComponents.DNA_GENE_CODE.get(), geneCode7);
            gene7.set(ModDataComponents.DNA_ERA_ID.get(), "modern");
            gene7.set(ModDataComponents.DNA_SLOT_NO.get(), 7);
            gene7.set(ModDataComponents.CONTAMINATED_SCORE.get(), contamPerGene);
            gene7.set(ModDataComponents.DNA_INTEGRITY_ID.get(), integPerGene);

            gene8.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesId8);
            gene8.set(ModDataComponents.DNA_GENE_CODE.get(), geneCode8);
            gene8.set(ModDataComponents.DNA_ERA_ID.get(), "modern");
            gene8.set(ModDataComponents.DNA_SLOT_NO.get(), 8);
            gene8.set(ModDataComponents.CONTAMINATED_SCORE.get(), contamPerGene);
            gene8.set(ModDataComponents.DNA_INTEGRITY_ID.get(), integPerGene);

            gene9.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesId9);
            gene9.set(ModDataComponents.DNA_GENE_CODE.get(), geneCode9);
            gene9.set(ModDataComponents.DNA_ERA_ID.get(), "modern");
            gene9.set(ModDataComponents.DNA_SLOT_NO.get(), 9);
            gene9.set(ModDataComponents.CONTAMINATED_SCORE.get(), contamPerGene);
            gene9.set(ModDataComponents.DNA_INTEGRITY_ID.get(), integPerGene);

            gene10.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesId10);
            gene10.set(ModDataComponents.DNA_GENE_CODE.get(), geneCode10);
            gene10.set(ModDataComponents.DNA_ERA_ID.get(), "modern");
            gene10.set(ModDataComponents.DNA_SLOT_NO.get(), 10);
            gene10.set(ModDataComponents.CONTAMINATED_SCORE.get(), contamPerGene);
            gene10.set(ModDataComponents.DNA_INTEGRITY_ID.get(), integPerGene);


            ItemStack existingOutput1 = this.itemHandler.getStackInSlot(OUTPUT_SLOT1);
            if(existingOutput1.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT1, gene1);
            } else {
                existingOutput1.grow(gene1.getCount());
            }
            ItemStack existingOutput2 = this.itemHandler.getStackInSlot(OUTPUT_SLOT2);
            if(existingOutput2.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT2, gene2);
            } else {
                existingOutput2.grow(gene2.getCount());
            }
            ItemStack existingOutput3 = this.itemHandler.getStackInSlot(OUTPUT_SLOT3);
            if(existingOutput3.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT3, gene3);
            } else {
                existingOutput3.grow(gene3.getCount());
            }
            ItemStack existingOutput4 = this.itemHandler.getStackInSlot(OUTPUT_SLOT4);
            if(existingOutput4.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT4, gene4);
            } else {
                existingOutput4.grow(gene4.getCount());
            }
            ItemStack existingOutput5 = this.itemHandler.getStackInSlot(OUTPUT_SLOT5);
            if(existingOutput5.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT5, gene5);
            } else {
                existingOutput5.grow(gene5.getCount());
            }
            ItemStack existingOutput6 = this.itemHandler.getStackInSlot(OUTPUT_SLOT6);
            if(existingOutput6.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT6, gene6);
            } else {
                existingOutput6.grow(gene6.getCount());
            }
            ItemStack existingOutput7 = this.itemHandler.getStackInSlot(OUTPUT_SLOT7);
            if(existingOutput7.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT7, gene7);
            } else {
                existingOutput7.grow(gene7.getCount());
            }
            ItemStack existingOutput8 = this.itemHandler.getStackInSlot(OUTPUT_SLOT8);
            if(existingOutput8.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT8, gene8);
            } else {
                existingOutput8.grow(gene8.getCount());
            }
            ItemStack existingOutput9 = this.itemHandler.getStackInSlot(OUTPUT_SLOT9);
            if(existingOutput9.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT9, gene9);
            } else {
                existingOutput9.grow(gene9.getCount());
            }
            ItemStack existingOutput10 = this.itemHandler.getStackInSlot(OUTPUT_SLOT10);
            if(existingOutput10.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT10, gene10);
            } else {
                existingOutput10.grow(gene10.getCount());
            }


    }
    private void craftItem() {
        Optional<RecipeHolder<CentrifugeRecipe>> recipe = getCurrentRecipe();
        ItemStack output1 = recipe.get().value().output1();
        ItemStack output2 = recipe.get().value().output2();

        itemHandler.extractItem(INPUT_SLOT1, 1, false);
        itemHandler.extractItem(INPUT_SLOT2, 1,false);

        itemHandler.setStackInSlot(OUTPUT_SLOT1, new ItemStack(output1.getItem(), itemHandler.getStackInSlot(OUTPUT_SLOT1).getCount() + output1.getCount()));
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
    private boolean hasSplittingRecipe() {
        Optional<RecipeHolder<DnaSplittingCentrifuge>> recipe = getGeneRecipe();
        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack output1 = recipe.get().value().gene1();
        ItemStack output2 = recipe.get().value().gene2();
        ItemStack output3 = recipe.get().value().gene3();
        ItemStack output4 = recipe.get().value().gene4();
        ItemStack output5 = recipe.get().value().gene5();
        ItemStack output6 = recipe.get().value().gene6();
        ItemStack output7 = recipe.get().value().gene7();
        ItemStack output8 = recipe.get().value().gene8();
        ItemStack output9 = recipe.get().value().gene9();
        ItemStack output10 = recipe.get().value().gene10();

        return canInsertAllGenesIntoOutputSlot(output1, output2,output3,output4,output5,output6,output7,output8,output9,output10) &&
                canInsertGeneAmountIntoOutputSlot(output1.getCount(),output2.getCount(),output3.getCount(),output4.getCount(),output5.getCount(),
                                                  output6.getCount(),output7.getCount(),output8.getCount(),output9.getCount(),output10.getCount());
        }
    private Optional<RecipeHolder<CentrifugeRecipe>> getCurrentRecipe() {
        return this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.CENTRIFUGE_TYPE.get(), new CentrifugeRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT1),itemHandler.getStackInSlot(INPUT_SLOT2)), level);
        }
    private Optional<RecipeHolder<DnaSplittingCentrifuge>> getGeneRecipe(){
        return this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.CENTRIFUGE_SPLITTING_TYPE.get(), new CentrifugeRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT1),itemHandler.getStackInSlot(INPUT_SLOT2)), level);
        }
    private boolean canInsertItemIntoOutputSlot(ItemStack output1, ItemStack output2) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT1).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT1).getItem() == output1.getItem()&&
                itemHandler.getStackInSlot(OUTPUT_SLOT2).isEmpty()|| this.itemHandler.getStackInSlot(OUTPUT_SLOT2).getItem() == output2.getItem();
        }
    private boolean canInsertAllGenesIntoOutputSlot(ItemStack output1, ItemStack output2, ItemStack output3,ItemStack output4, ItemStack output5, ItemStack output6,ItemStack output7, ItemStack output8, ItemStack output9, ItemStack output10) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT1).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT1).getItem() == output1.getItem()&&
                itemHandler.getStackInSlot(OUTPUT_SLOT2).isEmpty()|| this.itemHandler.getStackInSlot(OUTPUT_SLOT2).getItem() == output2.getItem()&&
                itemHandler.getStackInSlot(OUTPUT_SLOT3).isEmpty()|| this.itemHandler.getStackInSlot(OUTPUT_SLOT3).getItem() == output3.getItem()&&
                itemHandler.getStackInSlot(OUTPUT_SLOT4).isEmpty()|| this.itemHandler.getStackInSlot(OUTPUT_SLOT4).getItem() == output4.getItem()&&
                itemHandler.getStackInSlot(OUTPUT_SLOT5).isEmpty()|| this.itemHandler.getStackInSlot(OUTPUT_SLOT5).getItem() == output5.getItem()&&
                itemHandler.getStackInSlot(OUTPUT_SLOT6).isEmpty()|| this.itemHandler.getStackInSlot(OUTPUT_SLOT6).getItem() == output6.getItem()&&
                itemHandler.getStackInSlot(OUTPUT_SLOT7).isEmpty()|| this.itemHandler.getStackInSlot(OUTPUT_SLOT7).getItem() == output7.getItem()&&
                itemHandler.getStackInSlot(OUTPUT_SLOT8).isEmpty()|| this.itemHandler.getStackInSlot(OUTPUT_SLOT8).getItem() == output8.getItem()&&
                itemHandler.getStackInSlot(OUTPUT_SLOT9).isEmpty()|| this.itemHandler.getStackInSlot(OUTPUT_SLOT9).getItem() == output9.getItem()&&
                itemHandler.getStackInSlot(OUTPUT_SLOT10).isEmpty()|| this.itemHandler.getStackInSlot(OUTPUT_SLOT10).getItem() == output10.getItem();
        }

    private boolean canInsertAmountIntoOutputSlot(int count1, int count2) {
        int maxCount1 = itemHandler.getStackInSlot(OUTPUT_SLOT1).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT1).getMaxStackSize();
        int currentCount1 = itemHandler.getStackInSlot(OUTPUT_SLOT1).getCount();
        int maxCount2 = itemHandler.getStackInSlot(OUTPUT_SLOT2).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT2).getMaxStackSize();
        int currentCount2 = itemHandler.getStackInSlot(OUTPUT_SLOT2).getCount();

        return maxCount1 >= currentCount1 + count1 &&
                maxCount2 >= currentCount2 + count2;
        }
    private boolean canInsertGeneAmountIntoOutputSlot(int count1, int count2, int count3, int count4, int count5, int count6, int count7,int count8, int count9, int count10) {
        int maxCount1 = itemHandler.getStackInSlot(OUTPUT_SLOT1).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT1).getMaxStackSize();
        int currentCount1 = itemHandler.getStackInSlot(OUTPUT_SLOT1).getCount();
        int maxCount2 = itemHandler.getStackInSlot(OUTPUT_SLOT2).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT2).getMaxStackSize();
        int currentCount2 = itemHandler.getStackInSlot(OUTPUT_SLOT2).getCount();
        int maxCount3 = itemHandler.getStackInSlot(OUTPUT_SLOT3).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT3).getMaxStackSize();
        int currentCount3 = itemHandler.getStackInSlot(OUTPUT_SLOT3).getCount();
        int maxCount4 = itemHandler.getStackInSlot(OUTPUT_SLOT4).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT4).getMaxStackSize();
        int currentCount4 = itemHandler.getStackInSlot(OUTPUT_SLOT4).getCount();
        int maxCount5 = itemHandler.getStackInSlot(OUTPUT_SLOT5).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT5).getMaxStackSize();
        int currentCount5 = itemHandler.getStackInSlot(OUTPUT_SLOT5).getCount();
        int maxCount6 = itemHandler.getStackInSlot(OUTPUT_SLOT6).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT6).getMaxStackSize();
        int currentCount6 = itemHandler.getStackInSlot(OUTPUT_SLOT6).getCount();
        int maxCount7 = itemHandler.getStackInSlot(OUTPUT_SLOT7).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT7).getMaxStackSize();
        int currentCount7 = itemHandler.getStackInSlot(OUTPUT_SLOT7).getCount();
        int maxCount8 = itemHandler.getStackInSlot(OUTPUT_SLOT8).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT8).getMaxStackSize();
        int currentCount8 = itemHandler.getStackInSlot(OUTPUT_SLOT8).getCount();
        int maxCount9 = itemHandler.getStackInSlot(OUTPUT_SLOT9).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT9).getMaxStackSize();
        int currentCount9 = itemHandler.getStackInSlot(OUTPUT_SLOT9).getCount();
        int maxCount10 = itemHandler.getStackInSlot(OUTPUT_SLOT10).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT10).getMaxStackSize();
        int currentCount10 = itemHandler.getStackInSlot(OUTPUT_SLOT10).getCount();

        return maxCount1 >= currentCount1 + count1 &&
                maxCount2 >= currentCount2 + count2 &&
                maxCount3 >= currentCount3 + count3 &&
                maxCount4 >= currentCount4 + count4 &&
                maxCount5 >= currentCount5 + count5 &&
                maxCount6 >= currentCount6 + count6 &&
                maxCount7 >= currentCount7 + count7 &&
                maxCount8 >= currentCount8 + count8 &&
                maxCount9 >= currentCount9 + count9 &&
                maxCount10 >= currentCount10 + count10;
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