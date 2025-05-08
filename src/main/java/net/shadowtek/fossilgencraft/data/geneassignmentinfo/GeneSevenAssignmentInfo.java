package net.shadowtek.fossilgencraft.data.geneassignmentinfo;

import net.minecraft.resources.ResourceLocation;

public record GeneSevenAssignmentInfo(
        ResourceLocation speciesId,
        ResourceLocation genesById,

        String geneType,

        int gene_id,
        ResourceLocation pathToTextureLocation



) {

}
