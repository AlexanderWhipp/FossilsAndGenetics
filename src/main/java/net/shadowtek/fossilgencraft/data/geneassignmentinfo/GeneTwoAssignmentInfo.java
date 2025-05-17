package net.shadowtek.fossilgencraft.data.geneassignmentinfo;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record GeneTwoAssignmentInfo(
        ResourceLocation speciesId,
        ResourceLocation genesById,

        String geneType,

        int gene_id,
        ResourceLocation pathToTextureLocation,
        ResourceLocation pathToModelLocation



) {

}
