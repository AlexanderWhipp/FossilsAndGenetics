package net.shadowtek.fossilgencraft.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.shadowtek.fossilgencraft.FossilGenCraft;

public class ModTags {
    public static class Blocks {



        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, name));

        }
    }

    public static class Items {


        public static final TagKey<Item> SMELTABLE_TO_ASH = createTag("smeltable_to_ash");
        public static final TagKey<Item> TREX_DNA_ITEM = createTag("trex_dna_item");


        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, name));
        }
    }
}

