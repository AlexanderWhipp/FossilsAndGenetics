package net.shadowtek.fossilgencraft.data.geneassignmentinfo;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record GeneSevenAssignmentInfo(
        ResourceLocation speciesId,
        ResourceLocation genesById,

        String geneType,
        List<GeneSevenGoals> geneSevenGoals
) {
public record GeneSevenGoals(String goal){}
}
