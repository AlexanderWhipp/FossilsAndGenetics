package net.shadowtek.fossilgencraft.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.block.ModBlocks;
import net.shadowtek.fossilgencraft.block.custom.AmberLampBlock;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FossilGenCraft.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //ores
        blockWithItem(ModBlocks.AMBER_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_AMBER_ORE);
        blockWithItem(ModBlocks.FOSSIL_ORE);
        blockWithItem(ModBlocks.NETHER_SULFUR_ORE);

        //machines
        blockWithItem(ModBlocks.CENTRIFUGE);
        blockWithItem(ModBlocks.FREEZEDRYER);
        blockWithItem(ModBlocks.DNA_SEQUENCER);

        //Gensplicer Multiblock
        blockWithItem(ModBlocks.GENE_SPLICER_COMPUTER);

        blockWithItem(ModBlocks.SUPER_COMPUTER_TERMINAL);




    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),cubeAll(blockRegistryObject.get()));
    }
}
