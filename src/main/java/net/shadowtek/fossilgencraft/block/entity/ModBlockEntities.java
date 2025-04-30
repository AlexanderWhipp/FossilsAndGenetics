package net.shadowtek.fossilgencraft.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.block.ModBlocks;
import net.shadowtek.fossilgencraft.block.entity.custom.*;
import net.shadowtek.fossilgencraft.block.entity.custom.supercomputer.SuperComputerTerminalBlockEntity;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, FossilGenCraft.MOD_ID);

    public static final RegistryObject<BlockEntityType<AmberExtractorBlockEntity>>  AMBER_EXTRACTOR_BE =
            BLOCK_ENTITIES.register("amber_extractor_be", () -> BlockEntityType.Builder.of(
                    AmberExtractorBlockEntity::new, ModBlocks.AMBER_EXTRACTOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<CentrifugeBlockEntity>>  CENTRIFUGE_BE =
            BLOCK_ENTITIES.register("centrifuge_be", () -> BlockEntityType.Builder.of(
                    CentrifugeBlockEntity::new, ModBlocks.CENTRIFUGE.get()).build(null));
    public static final RegistryObject<BlockEntityType<FreezeDryerBlockEntity>> FREEZEDRYER_BE =
            BLOCK_ENTITIES.register("freezedryer_be", () -> BlockEntityType.Builder.of(
                    FreezeDryerBlockEntity::new, ModBlocks.FREEZEDRYER.get()).build(null));
    public static final RegistryObject<BlockEntityType<DnaSequencerBlockEntity>> DNA_SEQUENCER_BE =
            BLOCK_ENTITIES.register("dna_sequencer_be", () -> BlockEntityType.Builder.of(
                    DnaSequencerBlockEntity::new, ModBlocks.DNA_SEQUENCER.get()).build(null));
    public static final RegistryObject<BlockEntityType<SplicerBlockEntity>> GENE_SPLICER_COMPUTER_BE =
            BLOCK_ENTITIES.register("gene_splicer_computer_be", () -> BlockEntityType.Builder.of(
                   SplicerBlockEntity::new, ModBlocks.GENE_SPLICER_COMPUTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<SuperComputerTerminalBlockEntity>> SUPER_COMPUTER_TERMINAL_BE =
            BLOCK_ENTITIES.register("super_computer_terminal_be", () -> BlockEntityType.Builder.of(
                    SuperComputerTerminalBlockEntity::new, ModBlocks.SUPER_COMPUTER_TERMINAL.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
