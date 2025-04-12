package net.shadowtek.fossilgencraft.event;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.item.ModItems;



public class ModLootEvents {

    private static final ResourceLocation PIG_LOOT_TABLE_ID = ResourceLocation.fromNamespaceAndPath("minecraft", "entities/pig");


    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation tableName = event.getName();

        if (tableName.equals(PIG_LOOT_TABLE_ID)) {
            LootPool customPool = LootPool.lootPool()
                    .name(FossilGenCraft.MOD_ID + "_pig_fats")
                    .setRolls(ConstantValue.exactly(1))
                    .add(LootItem.lootTableItem(ModItems.PIG_FAT.get())
                            .setWeight(1)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                    )
                    .when(LootItemKilledByPlayerCondition.killedByPlayer())
                    .when(LootItemRandomChanceCondition.randomChance(0.25f))

                    .build();
            event.getTable().addPool(customPool);


        }

    }

    public static void register(IEventBus modEventBus) {
        MinecraftForge.EVENT_BUS.addListener(ModLootEvents::onLootTableLoad);

    }
}
