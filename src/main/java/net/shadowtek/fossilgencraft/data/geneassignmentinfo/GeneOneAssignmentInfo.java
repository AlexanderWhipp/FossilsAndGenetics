package net.shadowtek.fossilgencraft.data.geneassignmentinfo;

import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Array;
import java.util.List;

public record GeneOneAssignmentInfo(
        ResourceLocation speciesId,
        ResourceLocation genesById,

        String geneType,

        String modelLocation,
        String baseTextureLocation,
        String walkAnimationLocation,

        boolean landCheck,
        boolean waterCheck,
        boolean flyingCheck,

        String creature_size,
        int gene_id,
        List<BaseAttributeValue> base_attribute_values
) {
    public record BaseAttributeValue(String attribute, double attribute_value){}
}
