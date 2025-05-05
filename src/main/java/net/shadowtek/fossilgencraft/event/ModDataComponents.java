package net.shadowtek.fossilgencraft.event;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

public class ModDataComponents {
    private static final Codec<Set<String>> SET_OF_STRINGS_CODEC =
            Codec.STRING.listOf().xmap(HashSet::new, ArrayList::new);
    private static final StreamCodec<FriendlyByteBuf, Set<String>> SET_OF_STRINGS_STREAM_CODEC =
            ByteBufCodecs.collection(HashSet::new, ByteBufCodecs.STRING_UTF8);
    // Create DeferredRegister for DataComponentType registry
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, FossilGenCraft.MOD_ID);

    // DNA Components
    public static final RegistryObject<DataComponentType<String>> DNA_SPECIES_ID =
            DATA_COMPONENT_TYPES.register("dna_species_id", () ->
                    DataComponentType.<String>builder()
                            // Codec for saving/loading (disk)
                            .persistent(Codec.STRING)
                            // StreamCodec for networking
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            // Optional: .cacheEncoding() for performance reasons
                            .build()
            );
    public static final RegistryObject<DataComponentType<String>> DNA_ERA_ID =
            DATA_COMPONENT_TYPES.register("dna_era_id", () ->
                    DataComponentType.<String>builder()
                            // Codec for saving/loading (disk)
                            .persistent(Codec.STRING)
                            // StreamCodec for networking
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            // Optional: .cacheEncoding() for performance reasons
                            .build()
            );
    public static final RegistryObject<DataComponentType<Float>> DNA_INTEGRITY_ID =
            DATA_COMPONENT_TYPES.register("dna_integrity_id", () ->
                    DataComponentType.<Float>builder()
                            .persistent(Codec.FLOAT)
                            .networkSynchronized(ByteBufCodecs.FLOAT)
                            .build()
            );
    public static final RegistryObject<DataComponentType<Integer>> CONTAMINATED_SCORE =
            DATA_COMPONENT_TYPES.register("contamination_score", () ->
                    DataComponentType.<Integer>builder()
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT)
                            .build()
            );

    @Deprecated //Will be removing - no longer using this system - no longer using this system moving forward
    public static final RegistryObject<DataComponentType<Integer>> DNA_CHAIN_START_POS =
            DATA_COMPONENT_TYPES.register("dna_chain_start_pos", () ->
                    DataComponentType.<Integer>builder()
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT)
                            .build()
            );
    @Deprecated //Will be removing - no longer using this system moving forward
    public static final RegistryObject<DataComponentType<Integer>> DNA_CHAIN_END_POS =
            DATA_COMPONENT_TYPES.register("dna_chain_end_pos", () ->
                    DataComponentType.<Integer>builder()
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT)
                            .build()
            );


    //New DNA system

    //Genetic code for smallest fragments - 4 Characters long
    public static final RegistryObject<DataComponentType<String>> DNA_GENE_CHAIN_CODE =
            DATA_COMPONENT_TYPES.register("dna_gene_chain_code", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );

    //Genetic code for a full gene - 16 characters long
    public static final RegistryObject<DataComponentType<String>> DNA_GENE_CODE =
            DATA_COMPONENT_TYPES.register("dna_gene_code", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );

            public static final RegistryObject<DataComponentType<String>> DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_1 =
            DATA_COMPONENT_TYPES.register("dna_slot_label_1", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );
            public static final RegistryObject<DataComponentType<String>> DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_2 =
            DATA_COMPONENT_TYPES.register("dna_slot_label_2", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );
            public static final RegistryObject<DataComponentType<String>> DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_3 =
            DATA_COMPONENT_TYPES.register("dna_slot_label_3", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );
            public static final RegistryObject<DataComponentType<String>> DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_4 =
            DATA_COMPONENT_TYPES.register("dna_slot_label_4", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );
            public static final RegistryObject<DataComponentType<String>> DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_5 =
            DATA_COMPONENT_TYPES.register("dna_slot_label_5", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );
            public static final RegistryObject<DataComponentType<String>> DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_6 =
            DATA_COMPONENT_TYPES.register("dna_slot_label_6", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );
            public static final RegistryObject<DataComponentType<String>> DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_7 =
            DATA_COMPONENT_TYPES.register("dna_slot_label_7", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );
            public static final RegistryObject<DataComponentType<String>> DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_8 =
            DATA_COMPONENT_TYPES.register("dna_slot_label_8", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );
            public static final RegistryObject<DataComponentType<String>> DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_9 =
            DATA_COMPONENT_TYPES.register("dna_slot_label_9", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );
            public static final RegistryObject<DataComponentType<String>> DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_10 =
            DATA_COMPONENT_TYPES.register("dna_slot_label_10", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );

    //Full Genetic code for a creature. 320 characters long
    public static final RegistryObject<DataComponentType<String>> DNA_FULL_GENOME_CODE =
            DATA_COMPONENT_TYPES.register("dna_full_genome_code", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );







    public static final RegistryObject<DataComponentType<Integer>> DNA_SLOT_NO =
            DATA_COMPONENT_TYPES.register("dna_slot_no", () ->
                    DataComponentType.<Integer>builder()
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT)
                            .build()
            );
    public static final RegistryObject<DataComponentType<String>> DNA_TYPE_ID =
            DATA_COMPONENT_TYPES.register("dna_type_id", () ->
                    DataComponentType.<String>builder()
                            // Codec for saving/loading (disk)
                            .persistent(Codec.STRING)
                            // StreamCodec for networking
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            // Optional: .cacheEncoding() for performance reasons
                            .build()
            );

    public static final RegistryObject<DataComponentType<Set<String>>> COMPLETED_RESEARCH =
            DATA_COMPONENT_TYPES.register("completed_research", () ->
                    DataComponentType.<Set<String>>builder()
                            .persistent(SET_OF_STRINGS_CODEC)
                            .networkSynchronized(SET_OF_STRINGS_STREAM_CODEC)
                            .build()
            );


    // Register the DeferredRegister to the event bus
    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
