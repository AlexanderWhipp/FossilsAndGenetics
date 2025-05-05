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
        String eraId = pStack.get(ModDataComponents.DNA_ERA_ID.get());
        int isContaminated = pStack.getOrDefault(ModDataComponents.CONTAMINATED_SCORE.get(), 0);
        Number integrityLevelId = pStack.getOrDefault(ModDataComponents.DNA_INTEGRITY_ID.get(), 100);
        String fullGenome = pStack.getOrDefault(ModDataComponents.DNA_FULL_GENOME_CODE.get(), "Unreadable");

        String geneLabel1 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_1.get());
        String geneLabel2 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_2.get());
        String geneLabel3 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_3.get());
        String geneLabel4 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_4.get());
        String geneLabel5 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_5.get());
        String geneLabel6 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_6.get());
        String geneLabel7 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_7.get());
        String geneLabel8 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_8.get());
        String geneLabel9 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_9.get());
        String geneLabel10 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_10.get());





                if (speciesId != null && !speciesId.isEmpty()) {
                    pTooltipComponents.add(
                            Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.species", speciesId)
                                    .withStyle(ChatFormatting.RED));
                    pTooltipComponents.add(
                            Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.contamination", isContaminated)
                                    .withStyle(ChatFormatting.YELLOW));
                    pTooltipComponents.add(
                            Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.integrity_level", integrityLevelId)
                                    .withStyle(ChatFormatting.AQUA));
                    pTooltipComponents.add(
                            Component.translatable("tooltip.fossilgencraft.dna.full_genome", fullGenome)
                                    .withStyle(ChatFormatting.GOLD));

                    if(geneLabel1 !=null && !geneLabel1.equals(speciesId) && !geneLabel1.isEmpty()){
                        Component.translatable("tooltip.fossilgencraft.dna.gene_label_1", geneLabel1);
                    }
                    if(geneLabel2 !=null && !geneLabel2.equals(speciesId) && !geneLabel2.isEmpty()){
                        Component.translatable("tooltip.fossilgencraft.dna.gene_label_2", geneLabel2);
                    }

                    if(geneLabel3 !=null && !geneLabel3.equals(speciesId)){
                        Component.translatable("tooltip.fossilgencraft.dna.gene_label_3", geneLabel3);
                    }
                    if(geneLabel4 !=null && !geneLabel4.equals(speciesId)){
                        Component.translatable("tooltip.fossilgencraft.dna.gene_label_4", geneLabel4);
                    }

                    if(geneLabel5 !=null && !geneLabel5.equals(speciesId)){
                        Component.translatable("tooltip.fossilgencraft.dna.gene_label_5", geneLabel5);
                    }
                    if(geneLabel6 !=null && !geneLabel6.equals(speciesId)){
                        Component.translatable("tooltip.fossilgencraft.dna.gene_label_6", geneLabel6);
                    }
                    if(geneLabel7 !=null && !geneLabel7.equals(speciesId)){
                        Component.translatable("tooltip.fossilgencraft.dna.gene_label_7", geneLabel7);
                    }

                    if(geneLabel8 !=null && !geneLabel8.equals(speciesId)){
                        Component.translatable("tooltip.fossilgencraft.dna.gene_label_8", geneLabel8);
                    }
                    if(geneLabel9 !=null && !geneLabel9.equals(speciesId)){
                        Component.translatable("tooltip.fossilgencraft.dna.gene_label_9", geneLabel9);
                    }

                    if(geneLabel10 !=null && !geneLabel10.equals(speciesId)){
                        Component.translatable("tooltip.fossilgencraft.dna.gene_label_10", geneLabel10);
                    }


                } else {
                    pTooltipComponents.add(
                            Component.translatable("tooltip.fossilgencraft.sequenced_dna_base_chain.unknown")
                                    .withStyle(ChatFormatting.DARK_GRAY)

                    );
                    System.out.println("Species: " + speciesId);
                }

            }


}

