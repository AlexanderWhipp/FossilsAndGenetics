package net.shadowtek.fossilgencraft.data.geneassignmentinfo;

import net.minecraft.resources.ResourceLocation;

public record GeneNineAssignmentInfo(
        ResourceLocation speciesId,
        ResourceLocation genesById,

        String geneType,

        boolean canHerd



) {

}
