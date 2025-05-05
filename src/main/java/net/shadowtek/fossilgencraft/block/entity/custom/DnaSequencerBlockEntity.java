package net.shadowtek.fossilgencraft.block.entity.custom;

import net.minecraft.client.OptionInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.block.entity.ModBlockEntities;
import net.shadowtek.fossilgencraft.core.registries.ModDinosaurSpecies;
import net.shadowtek.fossilgencraft.data.species.dinosaurs.DinosaurSpecies;
import net.shadowtek.fossilgencraft.data.species.dinosaurs.fixedtraits.DinosaurPeriod;
import net.shadowtek.fossilgencraft.event.ModDataComponents;
import net.shadowtek.fossilgencraft.item.ModItems;
import net.shadowtek.fossilgencraft.recipe.*;

import net.shadowtek.fossilgencraft.screen.custom.DnaSequencerMenu;
import org.jetbrains.annotations.Nullable;


import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

import static net.shadowtek.fossilgencraft.data.species.dinosaurs.fixedtraits.DinosaurPeriod.JURASSIC;

public class DnaSequencerBlockEntity extends BlockEntity implements MenuProvider {

/*
    private static int dnaChainPos;
private static int dnaSegment = 10;

    private static final List<dnaChainPos> VALID_SEGMENTS = List.of(
            new dnaSegment(10)

    );

    private static final Random randomSegment = new Random();
Feature is W.I.P facing many technical challenges implementing at the moment, this is a future shadow problem

*/


    public final ItemStackHandler itemHandler = new ItemStackHandler(4){

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



    public DnaSequencerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DNA_SEQUENCER_BE.get(), pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int i){
                return switch (i){
                    case 0 -> DnaSequencerBlockEntity.this.progress;
                    case 1 -> DnaSequencerBlockEntity.this.maxProgress;
                    default -> 0;
                };

            }
            @Override
            public void set(int i, int value){
                switch (i) {
                    case 0:
                        DnaSequencerBlockEntity.this.progress = value;
                    case 1:
                        DnaSequencerBlockEntity.this.maxProgress = value;
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
        pTag.putInt("dna_sequencer.progress", progress);
        pTag.putInt("dna_sequencer.max_progress", maxProgress);

        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        progress = pTag.getInt("dna_sequencer.progress");
        maxProgress = pTag.getInt("dna_sequencer.max_progress");

    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.fossilgencraft.dna_sequencer");
    }
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new DnaSequencerMenu(pContainerId, pPlayerInventory, this, this.data);
    }
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level, blockPos, blockState);

            if (hasCraftingFinished() && itemHandler.getStackInSlot(INPUT_SLOT1).is(ModItems.CONTAMINATED_MOSQUITO_SAMPLE.get())) {
                craftItem();
                resetProgress();
            } else if (hasCraftingFinished() && itemHandler.getStackInSlot(INPUT_SLOT1).is(ModItems.SYRINGE_FILLED_BLOOD.get())){
                craftModerndna();
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
        Optional<RecipeHolder<DnaSequencerRecipe>> recipeOpt = getCurrentRecipe();
        if (recipeOpt.isEmpty()) { return; }
        DnaSequencerRecipe recipe = recipeOpt.get().value();

        // --- 1. Perform RNG to select output species ---
        if (this.level == null) return; // Safety check
        RandomSource random = this.level.random;

       var registeredDinosaurSpeciesEntries = ModDinosaurSpecies.DINOSAUR_SPECIES_DEFERRED_REGISTER.getEntries();

       if (registeredDinosaurSpeciesEntries.isEmpty()) { //Checks for registered dinosaurs before generating output, THIS SHOULD NEVER HAPPEN. If this error occurs the mod is broken
           System.err.println("NO DINOSAURS SPECIES REGISTERED! Cannot assign species to output!");
           resetProgress();
       }
        List<RegistryObject<DinosaurSpecies>> speciesList = List.copyOf(registeredDinosaurSpeciesEntries);
       RegistryObject<DinosaurSpecies> chosenSpeciesRO = speciesList.get(random.nextInt(speciesList.size()));
       String speciesIdString = chosenSpeciesRO.getId().getPath();
       System.err.println("Sequencer Randomly Selected Species: " + speciesIdString);
       // DnaSegment chosenSegment = VALID_SEGMENTS.get(random.nextInt(VALID_SEGMENTS.size()));
        int startPos = 10;
       // int endPos = chosenSegment.end();
        DinosaurSpecies species = chosenSpeciesRO.get();
        DinosaurPeriod period =  species.getDinosaurPeriod();
// Checks Origin Date of Selected Species and Assigns Associated Extraction integrity values, Only cretacious Fossils can have a chance of maximum integrity in a tier 1 sequencer
        //consider dropping these values, in favour of the tier 1 sequencer being maximised for Modern/Ice-Age Dna sources. Dinosaurs are the endgame of this mod!

        //Integrity defaults to 50 if Dinosaur is missing a time period (which it shouldnt! the setup does not allow for this!)
        float integrity = 100;

        switch (period) {
            case JURASSIC:
                integrity = 40F + random.nextFloat() * (60f-40f);
                break;
            case TRIASSIC:
                integrity = 1f + random.nextFloat() * (30f-1f);
                break;
            case CRETACIOUS:
                integrity = 50f + random.nextFloat() * (100f-50f);
                break;
        }
        integrity = Math.round(integrity * 10f) / 10f;
        int isContaminated = random.nextInt(11);



        // --- 2. Get Output Prototypes from the Recipe ---
        // Make sure your recipe record/class has appropriate getters like dnaResult() and residueResult()
        ItemStack dnaResultProto = recipe.output1();
        ItemStack residueResultProto = recipe.output2();
        // --- 3. Create the Sequenced DNA Stack & SET DATA COMPONENT --- <<< MODIFIED PART
        ItemStack sequencedDnaOutput = new ItemStack(ModItems.SEQUENCED_DNA_BASE_CHAIN.get(), dnaResultProto.getCount());


        // Instead of getOrCreateTag().putString():


        sequencedDnaOutput.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesIdString);
        sequencedDnaOutput.set(ModDataComponents.CONTAMINATED_SCORE.get(), isContaminated);
        sequencedDnaOutput.set(ModDataComponents.DNA_INTEGRITY_ID.get(), integrity);
        sequencedDnaOutput.set(ModDataComponents.DNA_CHAIN_START_POS.get(), startPos);
        // sequencedDnaOutput.set(ModDataComponents.DNA_CHAIN_END_POS.get(), endPos);
        // --- END OF MODIFIED PART ---

        // --- 4. Create the Residue Stack ---
        ItemStack residueOutput = residueResultProto.copy();

        // --- 5. Consume Inputs ---
        this.itemHandler.extractItem(INPUT_SLOT1, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT2, 1, false);

        // --- 6. Insert Outputs ---
        // (Using simplified logic - ensure it works for your inventory setup)
        ItemStack existingOutput1 = this.itemHandler.getStackInSlot(OUTPUT_SLOT1);
        if (existingOutput1.isEmpty()) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT1, sequencedDnaOutput);
        } else {
            existingOutput1.grow(sequencedDnaOutput.getCount());
        }

        ItemStack existingOutput2 = this.itemHandler.getStackInSlot(OUTPUT_SLOT2);
        if (existingOutput2.isEmpty()) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT2, residueOutput);
        } else {
            existingOutput2.grow(residueOutput.getCount());
        }
        resetProgress();
    }
    private void craftModerndna(){
        Optional<RecipeHolder<DnaSequencerRecipe>> recipeOpt = getCurrentRecipe();
        if (recipeOpt.isEmpty()) { return; }
        DnaSequencerRecipe recipe = recipeOpt.get().value();

        ItemStack dnaResultProto = recipe.output1();
        ItemStack residueResultProto = recipe.output2();

        String speciesIdString = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_SPECIES_ID.get(), "Unidentifiable Species");
        String speciesEraString = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_ERA_ID.get(), "modern");
        String speciesGeneticCodeString = itemHandler.getStackInSlot(INPUT_SLOT1).getOrDefault(ModDataComponents.DNA_FULL_GENOME_CODE.get(),"Unreadable");

        ItemStack residueOutput = residueResultProto.copy();

        ItemStack sequencedDnaOutput = new ItemStack(ModItems.COMPLETED_GENOME.get(), dnaResultProto.getCount());

        // --- 5. Consume Inputs ---
        this.itemHandler.extractItem(INPUT_SLOT1, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT2, 1, false);

        sequencedDnaOutput.set(ModDataComponents.DNA_SPECIES_ID.get(), speciesIdString);
        sequencedDnaOutput.set(ModDataComponents.DNA_ERA_ID.get(), speciesEraString);
        sequencedDnaOutput.set(ModDataComponents.DNA_FULL_GENOME_CODE.get(), speciesGeneticCodeString);

        ItemStack existingOutput1 = this.itemHandler.getStackInSlot(OUTPUT_SLOT1);
        if (existingOutput1.isEmpty()) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT1, sequencedDnaOutput);
        } else {
            existingOutput1.grow(sequencedDnaOutput.getCount());
        }

        ItemStack existingOutput2 = this.itemHandler.getStackInSlot(OUTPUT_SLOT2);
        if (existingOutput2.isEmpty()) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT2, residueOutput);
        } else {
            existingOutput2.grow(residueOutput.getCount());
        }
        resetProgress();
    }


    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }
    private void increaseCraftingProgress() {
        progress++;
    }
    private boolean hasRecipe() {
        Optional<RecipeHolder<DnaSequencerRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack output1 = recipe.get().value().output1();
        ItemStack output2 = recipe.get().value().output2();

        return canInsertItemIntoOutputSlot(output1, output2) && canInsertAmountIntoOutputSlot(output1.getCount(),output2.getCount());
    }
    private Optional<RecipeHolder<DnaSequencerRecipe>> getCurrentRecipe() {
        return this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.DNA_SEQUENCER_TYPE.get(), new DnaSequencerRecipeInput(itemHandler.getStackInSlot(INPUT_SLOT1),itemHandler.getStackInSlot(INPUT_SLOT2)), level);
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





}
