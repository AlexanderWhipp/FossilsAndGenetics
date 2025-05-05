package net.shadowtek.fossilgencraft.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.shadowtek.fossilgencraft.event.ModDataComponents;
import net.shadowtek.fossilgencraft.item.ModItems;

import java.util.List;

public class SyringeFilledBlood extends Item {
    public SyringeFilledBlood(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
        return new ItemStack(ModItems.SYRINGE_CONTAMINATED.get());
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        String speciesId = pStack.get(ModDataComponents.DNA_SPECIES_ID.get());
        String eraId = pStack.get(ModDataComponents.DNA_ERA_ID.get());
        String fullGeneticCode = pStack.get(ModDataComponents.DNA_FULL_GENOME_CODE.get());


        if (speciesId != null && !speciesId.isEmpty() ) {
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.species", speciesId)
                            .withStyle(ChatFormatting.RED));
            if (fullGeneticCode != null && !fullGeneticCode.isEmpty() ) {
                pTooltipComponents.add(
                        Component.translatable("tooltip.fossilgencraft.dna.full_genome", fullGeneticCode)
                                .withStyle(ChatFormatting.RED));
            }


        pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.dna.eraid", eraId)
                            .withStyle(ChatFormatting.RED));
        }
        else {
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.unknown")
                            .withStyle(ChatFormatting.DARK_GRAY)
            );
        }


    }
}

