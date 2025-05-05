package net.shadowtek.fossilgencraft.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;
import net.shadowtek.fossilgencraft.entity.custom.TRex001Entity;


public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FossilGenCraft.MOD_ID);

    public static final RegistryObject<EntityType<TRex001Entity>> TREX001 =
            ENTITY_TYPES.register("trexentity", () -> EntityType.Builder.of(TRex001Entity::new, MobCategory.CREATURE)
                    .sized(1.0f,1.0f).build("trexentity"));

    public static final RegistryObject<EntityType<GMOEntity>> CUSTOM_PLAYER_MOB =
            ENTITY_TYPES.register("custom_mob",
                    ()-> EntityType.Builder.of(GMOEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 1.75f)
                            .build(ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "custom_mob").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
