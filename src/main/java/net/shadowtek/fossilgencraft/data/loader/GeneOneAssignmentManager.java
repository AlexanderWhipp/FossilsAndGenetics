package net.shadowtek.fossilgencraft.data.loader;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneOneAssignmentInfo;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.*;

public class GeneOneAssignmentManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final String FOLDER_PATH = "dna/genes";

    private static Map<EntityType<?>, GeneOneAssignmentInfo> geneOneDataInfoMap = Collections.emptyMap();


    public GeneOneAssignmentManager() {
        super(GSON, FOLDER_PATH);
        FossilGenCraft.LOGGER.info("Gene One Assignment Manager Initialized, scanning folder '{}' ", FOLDER_PATH );
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        FossilGenCraft.LOGGER.info("Applying Gene Assignment data(Processing {} files)...", pObject.size());
        Map<EntityType<?>, GeneOneAssignmentInfo> newMap = new HashMap<>();

        for(Map.Entry<ResourceLocation, JsonElement> entry: pObject.entrySet()) {
            ResourceLocation fileId = entry.getKey();
            JsonElement element = entry.getValue();

            try {
                if(!element.isJsonObject()){
                    FossilGenCraft.LOGGER.error("Skipping Gene Assignment File {}: Root Element is not a JSON object", fileId);
                    continue;
                }
                JsonObject jsonObject = element.getAsJsonObject();

                String speciesIdString = GsonHelper.getAsString(jsonObject, "species_id");
                ResourceLocation speciesRL = ResourceLocation.parse(speciesIdString);

                EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.getOptional(speciesRL)
                        .orElseThrow(() -> new JsonSyntaxException("Unknown entity_id" + speciesRL + "in DNA extraction file: " + fileId));

                String genesByIdString = GsonHelper.getAsString(jsonObject, "genes_by_id");
                ResourceLocation genesByIdRl = ResourceLocation.parse(genesByIdString);

                JsonObject dataGeneSlot1 = GsonHelper.getAsJsonObject(jsonObject, "data_gene_slot_1");

                String geneType = GsonHelper.getAsString(dataGeneSlot1, "gene_type");
                boolean isAquatic = GsonHelper.getAsBoolean(dataGeneSlot1, "water_check");
                boolean canFly = GsonHelper.getAsBoolean(dataGeneSlot1, "flying_check");
                String creatureSize = GsonHelper.getAsString(dataGeneSlot1, "creature_size");
                int geneSpeciesIdNumber = GsonHelper.getAsInt(dataGeneSlot1, "gene_id");

               // JsonArray attributes = GsonHelper.getAsJsonArray(dataGeneSlot1, "base_attributes");
                Type listType = new TypeToken<List<GeneOneAssignmentInfo.BaseAttributeValue>>() {}.getType();

               List<GeneOneAssignmentInfo.BaseAttributeValue> attributes = GSON.fromJson(dataGeneSlot1.get("base_attribute_values"), listType);

                GeneOneAssignmentInfo info = new GeneOneAssignmentInfo(
                  speciesRL, genesByIdRl, geneType, isAquatic, canFly, creatureSize, geneSpeciesIdNumber, attributes
                );
                if(newMap.containsKey(entityType)){
                    FossilGenCraft.LOGGER.warn("Duplicate Gene Assignment Definition(over-writing previous)");
                }
                newMap.put(entityType, info);
                FossilGenCraft.LOGGER.debug("Loaded Gene 1 Data for entity {} from file {}", speciesIdString, fileId);

            } catch (Exception e) {
                FossilGenCraft.LOGGER.error("Failed to parse Gene Assignment file: {} - Error: {}", fileId, e.getMessage());
            }


        }
        geneOneDataInfoMap = newMap;
        FossilGenCraft.LOGGER.info("Finished applying Gene 1 Assignment Data. Loaded {} valid entries", geneOneDataInfoMap.size());

    }
    @Nullable
    public static GeneOneAssignmentInfo getGeneInfoForEntity(EntityType<?> entityType) {
        return geneOneDataInfoMap.get(entityType);
    }
}
