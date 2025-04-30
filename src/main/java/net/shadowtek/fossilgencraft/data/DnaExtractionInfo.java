package net.shadowtek.fossilgencraft.data;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public record DnaExtractionInfo (
    ResourceLocation entityId,
    ResourceLocation sampleItem,
    String dnaSpecies,
    String dnaType,
    float dnaIntegrity,
    int dnaContaminationScore,
    String dnaSlotBonus,
    int cooldown
) {

    }





