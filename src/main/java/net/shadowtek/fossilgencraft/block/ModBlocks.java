package net.shadowtek.fossilgencraft.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.block.custom.*;
import net.shadowtek.fossilgencraft.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, FossilGenCraft.MOD_ID);

    public static final RegistryObject<Block> AMBER_GEM_INSECT = registerBlock("amber_gem_insect",
            () -> new Block(BlockBehaviour.Properties.of().noOcclusion()
                    .strength(2f).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> AMBER_GEM_FLAWLESS = registerBlock("amber_gem_flawless",
            () -> new Block(BlockBehaviour.Properties.of().noOcclusion()
                    .strength(2f).sound(SoundType.AMETHYST)));
    public static final RegistryObject<Block> AMBER_LAMP = registerBlock("amber_lamp",
            () -> new AmberLampBlock(BlockBehaviour.Properties.of().noOcclusion().strength(1f)
                    .lightLevel(state -> state.getValue(AmberLampBlock.CLICKED) ? 7 : 0)));

    public static final RegistryObject<Block> AMBER_ORE = registerBlock("amber_ore",
            () -> new DropExperienceBlock(UniformInt.of( 2,4),BlockBehaviour.Properties.of()
                    .strength( 3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final RegistryObject<Block> DEEPSLATE_AMBER_ORE = registerBlock( "deepslate_amber_ore",
            () -> new DropExperienceBlock(UniformInt.of( 2,4),BlockBehaviour.Properties.of()
                    .strength( 4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> FOSSIL_ORE = registerBlock( "fossil_ore",
            () -> new DropExperienceBlock(UniformInt.of( 2,4),BlockBehaviour.Properties.of()
                    .strength( 3f).requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> NETHER_SULFUR_ORE = registerBlock( "nether_sulfur_ore",
            () -> new DropExperienceBlock(UniformInt.of( 2,4),BlockBehaviour.Properties.of()
                    .strength( 3f).requiresCorrectToolForDrops().sound(SoundType.NETHERRACK)));






//Machines
    public static final RegistryObject<Block> AMBER_EXTRACTOR = registerBlock("amber_extractor",
            () -> new AmberExtractorBlock(BlockBehaviour.Properties.of().noOcclusion()));
    public static final RegistryObject<Block> CENTRIFUGE = registerBlock("centrifuge",
            () -> new CentrifugeBlock(BlockBehaviour.Properties.of().strength( 3f)
                    .requiresCorrectToolForDrops().sound(SoundType.STONE)));
    public static final RegistryObject<Block> FREEZEDRYER = registerBlock("freezedryer",
            () -> new FreezeDryerBlock(BlockBehaviour.Properties.of().strength( 3f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> DNA_SEQUENCER = registerBlock("dna_sequencer",
            () -> new DnaSequencerBlock(BlockBehaviour.Properties.of().strength( 3f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GENE_SPLICER_COMPUTER = registerBlock("gene_splicer_computer",
            () -> new GeneSplicerComputerBlock(BlockBehaviour.Properties.of().strength( 3f).sound(SoundType.METAL)));




    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
