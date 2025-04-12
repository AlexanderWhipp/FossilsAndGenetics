package net.shadowtek.fossilgencraft.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.entity.custom.TRex001Entity;

import javax.swing.text.html.parser.Entity;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FossilGenCraft.MOD_ID);

    public static final RegistryObject<EntityType<TRex001Entity>> TREX001 =
            ENTITY_TYPES.register("trexentity", () -> EntityType.Builder.of(TRex001Entity::new, MobCategory.CREATURE)
                    .sized(1.0f,1.0f).build("trexentity"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
