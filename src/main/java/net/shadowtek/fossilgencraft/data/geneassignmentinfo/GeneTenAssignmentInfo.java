package net.shadowtek.fossilgencraft.data.geneassignmentinfo;

import net.minecraft.resources.ResourceLocation;

public record GeneTenAssignmentInfo(
        ResourceLocation speciesId,
        ResourceLocation genesById,

        String geneType,

        boolean aggressiveToPlayer,
        boolean tamable,
        boolean rideable,
        boolean defendOwner



) {

}
