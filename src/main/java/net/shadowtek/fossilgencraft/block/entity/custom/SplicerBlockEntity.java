package net.shadowtek.fossilgencraft.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.shadowtek.fossilgencraft.block.entity.ModBlockEntities;
import net.shadowtek.fossilgencraft.event.ModDataComponents;
import net.shadowtek.fossilgencraft.item.ModItems;
import net.shadowtek.fossilgencraft.recipe.AmberExtractorRecipeInput;
import net.shadowtek.fossilgencraft.recipe.ModRecipes;
import net.shadowtek.fossilgencraft.recipe.splicing.dinosaurs.DinosaurSplicingRecipe;
import net.shadowtek.fossilgencraft.recipe.splicing.dinosaurs.DinosaurSplicingRecipeInput;
import net.shadowtek.fossilgencraft.screen.custom.GeneSplicerMenu;
import org.jetbrains.annotations.NotNull;

//This block is going to be hell down the line but so cool once feature complete. This is the backbone of the whole genetics system, /**changes to this class can break the entire mod!!!!!!!**/
import javax.annotation.Nullable;
import java.util.Optional;

public class SplicerBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(13) {
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged(); // Mark dirty when inventory changes
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);

            }
        }
    };
    private static final int INPUT_SLOT_1 = 0;
    private static final int INPUT_SLOT_2 = 1;
    private static final int INPUT_SLOT_3 = 2;
    private static final int INPUT_SLOT_4 = 3;

    private static final int INPUT_SLOT_5 = 4;
    private static final int INPUT_SLOT_6 = 5;
    private static final int INPUT_SLOT_7 = 6;
    private static final int INPUT_SLOT_8 = 7;

    private static final int INPUT_SLOT_9 = 8;
    private static final int INPUT_SLOT_10 = 9;

    private static final int INPUT_SLOT_11 = 10;
    private static final int INPUT_SLOT_12 = 11;


    private static final int OUTPUT_SLOT = 12;


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    protected final ContainerData data;


    private int progress = 0;
    private int maxProgress = 72;


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
        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory").get());
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
            System.err.println("Recipe Recieved");
            increaseCraftingProgress();
            setChanged(level, blockpos, blockstate);


            if (hasCraftingFinished()) {
                  craftItem();
                  System.err.println("Crafting Successful");
                resetProgress();
            }
        } else {
            resetProgress();
            System.err.println("Oops there was a problem, starting again #1");
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 72;
    }

    private void craftItem() {
        Optional<RecipeHolder<DinosaurSplicingRecipe>> recipeOpt = getCurrentRecipe();
        if (recipeOpt.isEmpty()) {return;}
        DinosaurSplicingRecipe recipe = recipeOpt.get().value();


        float totalIntegrity = 0f;
        float integScore1 = itemHandler.getStackInSlot(0).getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 100f);
        float integScore2 = itemHandler.getStackInSlot(1).getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 100f);
        float integScore3 = itemHandler.getStackInSlot(2).getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 100f);
        float integScore4 = itemHandler.getStackInSlot(3).getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 100f);
        float integScore5 = itemHandler.getStackInSlot(4).getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 100f);
        float integScore6 = itemHandler.getStackInSlot(5).getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 100f);
        float integScore7 = itemHandler.getStackInSlot(6).getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 100f);
        float integScore8 = itemHandler.getStackInSlot(7).getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 100f);
        float integScore9 = itemHandler.getStackInSlot(8).getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 100f);
        float integScore10 = itemHandler.getStackInSlot(9).getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 100f);

        totalIntegrity = integScore1 + integScore2 + integScore3 + integScore4 + integScore5 + integScore6 + integScore7 + integScore8 + integScore9 + integScore10;

        float avareageQuality = totalIntegrity / 10;



        int totalContamination = itemHandler.getStackInSlot(0).getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(), 0) +
                itemHandler.getStackInSlot(1).getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(), 0) +
                itemHandler.getStackInSlot(2).getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(), 0) +
                itemHandler.getStackInSlot(3).getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(), 0) +
                itemHandler.getStackInSlot(4).getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(), 0) +
                itemHandler.getStackInSlot(5).getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(), 0) +
                itemHandler.getStackInSlot(6).getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(), 0) +
                itemHandler.getStackInSlot(7).getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(), 0) +
                itemHandler.getStackInSlot(8).getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(), 0) +
                itemHandler.getStackInSlot(9).getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(), 0);



        String speciesIdString = itemHandler.getStackInSlot(0).getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unidentifiable DNA");
        String speciesIdString2 = itemHandler.getStackInSlot(1).getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unidentifiable DNA");
        String speciesIdString3 = itemHandler.getStackInSlot(2).getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unidentifiable DNA");
        String speciesIdString4 = itemHandler.getStackInSlot(3).getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unidentifiable DNA");
        String speciesIdString5 = itemHandler.getStackInSlot(4).getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unidentifiable DNA");
        String speciesIdString6 = itemHandler.getStackInSlot(5).getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unidentifiable DNA");
        String speciesIdString7 = itemHandler.getStackInSlot(6).getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unidentifiable DNA");
        String speciesIdString8 = itemHandler.getStackInSlot(7).getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unidentifiable DNA");
        String speciesIdString9 = itemHandler.getStackInSlot(8).getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unidentifiable DNA");
        String speciesIdString10 = itemHandler.getStackInSlot(9).getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unidentifiable DNA");

        String geneCode1 = itemHandler.getStackInSlot(0).getOrDefault(ModDataComponents.DNA_GENE_CODE.get(), "AAAAAAAAAAAAAAAA");
        String geneCode2 = itemHandler.getStackInSlot(1).getOrDefault(ModDataComponents.DNA_GENE_CODE.get(), "BBBBBBBBBBBBBBBB");
        String geneCode3 = itemHandler.getStackInSlot(2).getOrDefault(ModDataComponents.DNA_GENE_CODE.get(), "CCCCCCCCCCCCCCCC");
        String geneCode4 = itemHandler.getStackInSlot(3).getOrDefault(ModDataComponents.DNA_GENE_CODE.get(), "DDDDDDDDDDDDDDDD");
        String geneCode5 = itemHandler.getStackInSlot(4).getOrDefault(ModDataComponents.DNA_GENE_CODE.get(), "EEEEEEEEEEEEEEEE");
        String geneCode6 = itemHandler.getStackInSlot(5).getOrDefault(ModDataComponents.DNA_GENE_CODE.get(), "FFFFFFFFFFFFFFFF");
        String geneCode7 = itemHandler.getStackInSlot(6).getOrDefault(ModDataComponents.DNA_GENE_CODE.get(), "GGGGGGGGGGGGGGGG");
        String geneCode8 = itemHandler.getStackInSlot(7).getOrDefault(ModDataComponents.DNA_GENE_CODE.get(), "HHHHHHHHHHHHHHHH");
        String geneCode9 = itemHandler.getStackInSlot(8).getOrDefault(ModDataComponents.DNA_GENE_CODE.get(), "IIIIIIIIIIIIIIII");
        String geneCode10 = itemHandler.getStackInSlot(9).getOrDefault(ModDataComponents.DNA_GENE_CODE.get(), "JJJJJJJJJJJJJJJJ");

        String fullGeneCode = geneCode1 + geneCode2 + geneCode3 + geneCode4 + geneCode5 + geneCode6 + geneCode7 + geneCode8 + geneCode9 + geneCode10;






            ItemStack genomeOutput = recipe.completedGenome();
            ItemStack completedGenomeOutput = new ItemStack(ModItems.COMPLETED_GENOME.get(), genomeOutput.getCount());


            completedGenomeOutput.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesIdString);
            completedGenomeOutput.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_1.get(), speciesIdString);
            completedGenomeOutput.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_2.get(), speciesIdString2);
            completedGenomeOutput.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_3.get(), speciesIdString3);
            completedGenomeOutput.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_4.get(), speciesIdString4);
            completedGenomeOutput.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_5.get(), speciesIdString5);
            completedGenomeOutput.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_6.get(), speciesIdString6);
            completedGenomeOutput.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_7.get(), speciesIdString7);
            completedGenomeOutput.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_8.get(), speciesIdString8);
            completedGenomeOutput.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_9.get(), speciesIdString9);
            completedGenomeOutput.set(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_10.get(), speciesIdString10);
            completedGenomeOutput.set(ModDataComponents.DNA_FULL_GENOME_CODE.get(), fullGeneCode);

            completedGenomeOutput.set(ModDataComponents.DNA_INTEGRITY_ID.get(), avareageQuality);
            completedGenomeOutput.set(ModDataComponents.CONTAMINATED_SCORE.get(), totalContamination);

            this.itemHandler.extractItem(INPUT_SLOT_1, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_2, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_3, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_4, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_5, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_6, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_7, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_8, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_9, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_10, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_11, 1, false);
            this.itemHandler.extractItem(INPUT_SLOT_12, 1, false);
            ItemStack existingOutput = this.itemHandler.getStackInSlot(OUTPUT_SLOT);
            if (existingOutput.isEmpty()) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT, completedGenomeOutput);
            } else {
                existingOutput.grow(completedGenomeOutput.getCount());
            }
            resetProgress();
        }




    private boolean hasCraftingFinished()
    {
        return this.progress >= this.maxProgress;

    }

    private void increaseCraftingProgress()
    {
        progress++;
    }

    // Checks if item is craftable
    private boolean hasRecipe()
    {
        Optional<RecipeHolder<DinosaurSplicingRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty())
        {
            return false;
        }

        ItemStack output = recipe.get().value().completedGenome();
        return canInsertItemIntoOutputSlot(output) && canInsertAmountIntoOutputSlot(output.getCount());

    }

    private Optional<RecipeHolder<DinosaurSplicingRecipe>> getCurrentRecipe()
    {
        return this.getLevel().getServer().getRecipeManager()
                .getRecipeFor(ModRecipes.DINO_DNA_TYPE.get(),
                        new DinosaurSplicingRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT_1), itemHandler.getStackInSlot(INPUT_SLOT_2), itemHandler.getStackInSlot(INPUT_SLOT_3),
                                itemHandler.getStackInSlot(INPUT_SLOT_4),itemHandler.getStackInSlot(INPUT_SLOT_5),itemHandler.getStackInSlot(INPUT_SLOT_6),
                                itemHandler.getStackInSlot(INPUT_SLOT_7), itemHandler.getStackInSlot(INPUT_SLOT_8),itemHandler.getStackInSlot(INPUT_SLOT_9),
                                itemHandler.getStackInSlot(INPUT_SLOT_10), itemHandler.getStackInSlot(INPUT_SLOT_11),itemHandler.getStackInSlot(INPUT_SLOT_12)), level);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output)
    {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty()  || this.itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count)
    {
        int maxCount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 1 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();


        return maxCount >= currentCount + count;
    }


}

