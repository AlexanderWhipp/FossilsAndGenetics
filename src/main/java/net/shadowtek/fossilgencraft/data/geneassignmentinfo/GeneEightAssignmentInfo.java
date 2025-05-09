package net.shadowtek.fossilgencraft.data.geneassignmentinfo;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record GeneEightAssignmentInfo(
        ResourceLocation speciesId,
        ResourceLocation genesById,

        String geneType,
        List<ValidFoodItems> foodItems





) {
public record ValidFoodItems(String item){}
}
