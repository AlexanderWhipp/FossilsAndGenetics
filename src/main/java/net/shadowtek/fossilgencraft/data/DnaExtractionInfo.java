package net.shadowtek.fossilgencraft.data;

import net.minecraft.resources.ResourceLocation;

public record DnaExtractionInfo (
    ResourceLocation entityId,
    ResourceLocation sampleItem,
    String dnaSpecies,
    String dnaType,
    float dnaIntegrity,
    int dnaContaminationScore,
    String dnaSlotBonus,
    int cooldown,
    String dnaEraId,
    String fullGeneCode) {

    }





