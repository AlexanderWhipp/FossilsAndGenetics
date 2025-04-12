package net.shadowtek.fossilgencraft.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.block.ModBlocks;
import net.shadowtek.fossilgencraft.block.custom.AmberExtractorBlock;
import net.shadowtek.fossilgencraft.item.ModItems;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries){
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        this.add(ModBlocks.DEEPSLATE_AMBER_ORE.get(),
                block -> createOreDrop(ModBlocks.DEEPSLATE_AMBER_ORE.get(), ModBlocks.AMBER_GEM_INSECT.get().asItem()));
        this.add(ModBlocks.AMBER_ORE.get(),
                block -> createOreDrop(ModBlocks.AMBER_ORE.get(), ModBlocks.AMBER_GEM_INSECT.get().asItem()));
        this.add(ModBlocks.FOSSIL_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.FOSSIL_ORE.get(), ModItems.FOSSIL_IMPRINT.get(), 1, 3));
        this.add(ModBlocks.NETHER_SULFUR_ORE.get(),
                block -> createMultipleOreDrops(ModBlocks.NETHER_SULFUR_ORE.get(), ModItems.SULFUR.get(),3,5));

        dropSelf(ModBlocks.AMBER_EXTRACTOR.get());
        dropSelf(ModBlocks.AMBER_GEM_INSECT.get());
        dropSelf(ModBlocks.AMBER_GEM_FLAWLESS.get());
        dropSelf(ModBlocks.AMBER_LAMP.get());
        dropSelf(ModBlocks.CENTRIFUGE.get());
        dropSelf(ModBlocks.FREEZEDRYER.get());
        dropSelf(ModBlocks.DNA_SEQUENCER.get());
        dropSelf(ModBlocks.GENE_SPLICER_COMPUTER.get());




    }
    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock, this.applyExplosionDecay(
                        pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks(){
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
