package net.shadowtek.fossilgencraft.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.shadowtek.fossilgencraft.event.ModDataComponents;

import java.util.List;

public class GeneChainItem extends Item {
    public GeneChainItem(Item.Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {

        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);

        // Get the species ID string from the Data Component
        // Use getOrDefault to handle cases where component might be missing (though it shouldn't be if Sequencer always adds it)
        // Provide "" or null as the default, then check it.
        String speciesId = pStack.get(ModDataComponents.DNA_SPECIES_ID.get());
        int isContaminated = pStack.getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(),0);
        Number integrityLevelId = pStack.getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 10);
        String geneticCode= pStack.getOrDefault(ModDataComponents.DNA_GENE_CODE.get(), "ATCG-ATCG-ATCG-ATCG");
        int slotNumber = pStack.getOrDefault(ModDataComponents.DNA_SLOT_NO.get(), 11);

        if (speciesId != null && !speciesId.isEmpty() ){
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.species", speciesId)
                            .withStyle(ChatFormatting.RED));
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.dna.individual_gene_code", geneticCode)
                            .withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.contamination", isContaminated)
                    .withStyle(ChatFormatting.RED));
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.integrity_level", integrityLevelId));
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.dna.gene_slot_number", slotNumber));


        } else {
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.unknown")
                            .withStyle(ChatFormatting.DARK_GRAY)
            );
        }
    }
}
