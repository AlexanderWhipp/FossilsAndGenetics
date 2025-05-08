package net.shadowtek.fossilgencraft.data.geneassignmentinfo;

import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record GeneThreeAssignmentInfo(
        ResourceLocation speciesId,
        ResourceLocation genesById,

        String geneType,
        String geneDescription,

        List<AttributeModifiers> geneThreeModifiers



) {
public record AttributeModifiers(String attribute,boolean modifierActive,double attributeValue){}
}
