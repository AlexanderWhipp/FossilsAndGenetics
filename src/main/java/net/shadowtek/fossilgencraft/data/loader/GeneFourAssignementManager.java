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
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneFiveAssignmentInfo;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneFourAssignmentInfo;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneFourAssignementManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final String FOLDER_PATH = "dna/genes";

    private static Map<String, GeneFourAssignmentInfo> geneFourAssignmentInfoMap = Collections.emptyMap();


    public GeneFourAssignementManager() {
        super(GSON, FOLDER_PATH);
        FossilGenCraft.LOGGER.info("Gene One Assignment Manager Initialized, scanning folder '{}' ", FOLDER_PATH );
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        FossilGenCraft.LOGGER.info("Applying Gene Assignment data(Processing {} files)...", pObject.size());
        Map<String, GeneFourAssignmentInfo> newMap = new HashMap<>();

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

                EntityType<?> entityTypefromRegistry = BuiltInRegistries.ENTITY_TYPE.getOptional(speciesRL)
                        .orElseThrow(() -> new JsonSyntaxException("Unknown entity_id" + speciesRL + "in DNA extraction file: " + fileId));
                String entityTypeString = entityTypefromRegistry.getDescriptionId();

                String genesByIdString = GsonHelper.getAsString(jsonObject, "genes_by_id");
                ResourceLocation genesByIdRl = ResourceLocation.parse(genesByIdString);

                JsonObject dataGeneSlot4 = GsonHelper.getAsJsonObject(jsonObject, "data_gene_slot_4");

                String geneType = GsonHelper.getAsString(dataGeneSlot4, "gene_type");
                String geneDescription = GsonHelper.getAsString(dataGeneSlot4, "gene_desc");

                Type listType = new TypeToken<List<GeneFourAssignmentInfo.GoalsToAdd>>() {}.getType();

                List<GeneFourAssignmentInfo.GoalsToAdd> goals = GSON.fromJson(dataGeneSlot4.get("goals_to_add"), listType);

                GeneFourAssignmentInfo info = new GeneFourAssignmentInfo(
                  speciesRL, genesByIdRl, geneType, geneDescription ,goals
                );
                if(newMap.containsKey(entityTypeString)){
                    FossilGenCraft.LOGGER.warn("Duplicate Gene Assignment Definition(over-writing previous)");
                }
                newMap.put(entityTypeString, info);
                FossilGenCraft.LOGGER.debug("Loaded Gene 4 Data for entity {} from file {}", speciesIdString, fileId);

            } catch (Exception e) {
                FossilGenCraft.LOGGER.error("Failed to parse Gene Assignment file: {} - Error: {}", fileId, e.getMessage());
            }


        }
        geneFourAssignmentInfoMap = newMap;
        FossilGenCraft.LOGGER.info("Finished applying Gene 4 Assignment Data. Loaded {} valid entries", geneFourAssignmentInfoMap.size());

    }
    @Nullable
    public static GeneFourAssignmentInfo getGeneFourInfoForEntity(String entityType) {
        return geneFourAssignmentInfoMap.get(entityType);
    }
}
