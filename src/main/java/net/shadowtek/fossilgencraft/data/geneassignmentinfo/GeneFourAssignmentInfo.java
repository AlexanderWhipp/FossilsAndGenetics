package net.shadowtek.fossilgencraft.data.geneassignmentinfo;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record GeneFourAssignmentInfo(
        ResourceLocation speciesId,
        ResourceLocation genesById,

        String geneType,
String geneDescription,
        List<GoalsToAdd> goalsToAdd


) {
public record GoalsToAdd(String goal, boolean is_active){}
}
