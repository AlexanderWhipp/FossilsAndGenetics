package net.shadowtek.fossilgencraft.event;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;

public class ModDataComponents {
    // Create DeferredRegister for DataComponentType registry
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, FossilGenCraft.MOD_ID);

    // Define your custom component type for the DNA Species ID (String)
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
    public static final RegistryObject<DataComponentType<String>> DNA_INTEGRITY_ID =
            DATA_COMPONENT_TYPES.register("dna_integrity_id", () ->
                    DataComponentType.<String>builder()
                            .persistent(Codec.STRING)
                            .networkSynchronized(ByteBufCodecs.STRING_UTF8)
                            .build()
            );
    public static final RegistryObject<DataComponentType<Boolean>> IS_CONTAMINATED =
            DATA_COMPONENT_TYPES.register("is_contaminated", () ->
                    DataComponentType.<Boolean>builder()
                            .persistent(Codec.BOOL)
                            .networkSynchronized(ByteBufCodecs.BOOL)
                            .build()
            );
    public static final RegistryObject<DataComponentType<Integer>> DNA_CHAIN_START_POS =
            DATA_COMPONENT_TYPES.register("dna_chain_start_pos", () ->
                    DataComponentType.<Integer>builder()
                            // Codec for saving/loading (disk)
                            .persistent(Codec.INT)
                            // StreamCodec for networking
                            .networkSynchronized(ByteBufCodecs.INT)
                            // Optional: .cacheEncoding() for performance reasons
                            .build()
            );
    public static final RegistryObject<DataComponentType<Integer>> DNA_CHAIN_END_POS =
            DATA_COMPONENT_TYPES.register("dna_chain_end_pos", () ->
                    DataComponentType.<Integer>builder()
                            // Codec for saving/loading (disk)
                            .persistent(Codec.INT)
                            // StreamCodec for networking
                            .networkSynchronized(ByteBufCodecs.INT)
                            // Optional: .cacheEncoding() for performance reasons
                            .build()
            );
    // Register the DeferredRegister to the event bus
    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
