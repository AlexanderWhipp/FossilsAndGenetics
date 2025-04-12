package net.shadowtek.fossilgencraft.worldgen;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.block.ModBlocks;

import java.lang.module.Configuration;
import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_AMBER_ORE_KEY = registerKey("amber_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_DEEPSLATE_AMBER_ORE_KEY = registerKey("deepslate_amber_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_FOSSIL_ORE_KEY = registerKey("fossil_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_SULFUR_ORE_KEY = registerKey("nether_sulfur_ore_key");


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherrackReplaceables = new TagMatchTest(BlockTags.NETHER_CARVER_REPLACEABLES );

        List<OreConfiguration.TargetBlockState> overworld_Amber_ores = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.AMBER_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_AMBER_ORE.get().defaultBlockState()));


        List<OreConfiguration.TargetBlockState> overworld_Fossil_ores = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.FOSSIL_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> nether_sulfur_ores = List.of(
                OreConfiguration.target(netherrackReplaceables, ModBlocks.NETHER_SULFUR_ORE.get().defaultBlockState()));


        register(context, OVERWORLD_FOSSIL_ORE_KEY, Feature.ORE, new OreConfiguration(overworld_Fossil_ores, 8));
        register(context, OVERWORLD_AMBER_ORE_KEY, Feature.ORE, new OreConfiguration(overworld_Amber_ores, 3));
        register(context, NETHER_SULFUR_ORE_KEY, Feature.ORE, new OreConfiguration(nether_sulfur_ores,7));



    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, name));


    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));

    }
}