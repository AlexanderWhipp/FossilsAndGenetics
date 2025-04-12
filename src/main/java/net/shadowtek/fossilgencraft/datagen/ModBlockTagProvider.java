package net.shadowtek.fossilgencraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, FossilGenCraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.AMBER_ORE.get())
                .add(ModBlocks.DEEPSLATE_AMBER_ORE.get())
                .add(ModBlocks.FOSSIL_ORE.get())
                .add(ModBlocks.CENTRIFUGE.get())
                .add(ModBlocks.NETHER_SULFUR_ORE.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.AMBER_ORE.get())
                .add(ModBlocks.DEEPSLATE_AMBER_ORE.get())
                .add(ModBlocks.CENTRIFUGE.get())
                .add(ModBlocks.NETHER_SULFUR_ORE.get());


        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.FOSSIL_ORE.get());

    }
}
