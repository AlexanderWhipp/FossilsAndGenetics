package net.shadowtek.fossilgencraft.data.geneassignmentinfo;

import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Array;
import java.util.List;

public record GeneOneAssignmentInfo(
        ResourceLocation species_id,
        ResourceLocation genes_by_id,

        String gene_type,
        boolean water_check,
        boolean flying_check,

        String creature_size,
        int gene_id,
        List<BaseAttributeValue> base_attribute_values
) {
    public record BaseAttributeValue(String attribute, double attribute_value){}
}
