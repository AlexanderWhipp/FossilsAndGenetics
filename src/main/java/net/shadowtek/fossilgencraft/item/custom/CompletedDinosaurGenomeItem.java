package net.shadowtek.fossilgencraft.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.shadowtek.fossilgencraft.event.ModDataComponents;

import java.util.List;

public class CompletedDinosaurGenomeItem extends Item {
    public CompletedDinosaurGenomeItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        // Get the species ID string from the Data Component
        // Use getOrDefault to handle cases where component might be missing (though it shouldn't be if Sequencer always adds it)
        // Provide "" or null as the default, then check it.
        String speciesId = pStack.get(ModDataComponents.DNA_SPECIES_ID.get());
        int isContaminated = pStack.getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(),0);
        Number integrityLevelId = pStack.getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 100);

        if (speciesId != null && !speciesId.isEmpty() ){
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.species", speciesId)
                            .withStyle(ChatFormatting.RED));
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.contamination", isContaminated)
                            .withStyle(ChatFormatting.YELLOW));
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.integrity_level", integrityLevelId)
                    .withStyle(ChatFormatting.AQUA));

        } else {
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.unknown")
                            .withStyle(ChatFormatting.DARK_GRAY)
            );
        }
    }
}

