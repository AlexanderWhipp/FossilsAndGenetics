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
    int dnaContaminationScore, // Using score component name
    String dnaSlotBonus,
    int cooldown
) {
        // Records automatically generate fields, constructor, getters, equals, etc.
        // The body can usually be empty for simple data holders like this.
    }





