package net.shadowtek.fossilgencraft.data.loader;

import com.google.gson.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.data.DnaExtractionInfo;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DnaExtractionManager extends SimpleJsonResourceReloadListener {

   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private static final String FOLDER_NAME = "dnaextractionvanilla";

   private static Map<EntityType<?>, DnaExtractionInfo> extractionDataMap = Collections.emptyMap();


    public DnaExtractionManager(){
    super(GSON, FOLDER_NAME);
        FossilGenCraft.LOGGER.info("DNA Extraction Manager Initialized, scanning folder '{}'", FOLDER_NAME);
    }








    @Override
    protected void apply(Map<ResourceLocation, JsonElement> loadedResources, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        FossilGenCraft.LOGGER.info("Applying DNA extraction data (Processing {} files)...", loadedResources.size());
    Map<EntityType<?>, DnaExtractionInfo> newMap = new HashMap<>();

    for (Map.Entry<ResourceLocation, JsonElement> entry: loadedResources.entrySet()) {
        ResourceLocation fileID = entry.getKey();
        JsonElement element = entry.getValue();

        try {
            if (!element.isJsonObject()) {
                FossilGenCraft.LOGGER.error("Skipping DNA extraction File {}: Root element is not a JSON object", fileID);
                continue;
            }
            JsonObject jsonObject = element.getAsJsonObject();

            String entityIdstring = GsonHelper.getAsString(jsonObject, "entity_id");
            ResourceLocation entityRl = ResourceLocation.parse(entityIdstring);

            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.getOptional(entityRl)
                    .orElseThrow(() -> new JsonSyntaxException("Unknown entity_id" + entityRl + "in DNA extraction file: " + fileID));

            String sampleItemString = GsonHelper.getAsString(jsonObject, "sample_item");
            ResourceLocation sampleItemRl = ResourceLocation.parse(sampleItemString);

            JsonObject componentObject = GsonHelper.getAsJsonObject(jsonObject, "components_on_sample");

            String species = GsonHelper.getAsString(componentObject, "fossilgencraft:dna_species_id");
            String type = GsonHelper.getAsString(componentObject, "fossilgencraft:dna_type_id");
            float integrity = GsonHelper.getAsFloat(componentObject, "fossilgencraft:dna_integrity_id");
            int contamScore = GsonHelper.getAsInt(componentObject, "fossilgencraft:contamination_score");
            String slotBonus = GsonHelper.getAsString(componentObject, "fossilgencraft:dna_slot_bonus");

            int cooldown = GsonHelper.getAsInt(jsonObject, "cooldown", 0);

            DnaExtractionInfo info = new DnaExtractionInfo(
                    entityRl, sampleItemRl, species, type, integrity, contamScore, slotBonus, cooldown
            );
            if (newMap.containsKey(entityType)) {
                FossilGenCraft.LOGGER.warn("Duplicate DNA extraction definition (overwriting previous)");
            }
            newMap.put(entityType, info);
            FossilGenCraft.LOGGER.debug("Loaded DNA extraction data for entity {} from file {}", entityIdstring, fileID);

        } catch (Exception e) {
            FossilGenCraft.LOGGER.error("Failed to parse DNA extraction file: {} - Error: {}", fileID, e.getMessage());
        }
    }

    extractionDataMap = newMap;
    FossilGenCraft.LOGGER.info("Finished applying DNA extraction data. Loaded {} valid entries", extractionDataMap.size());
    }

    @Nullable
    public static DnaExtractionInfo getInfoForEntity(EntityType<?> entityType) {
        return extractionDataMap.get(entityType);
    }

}
