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
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneNineAssignmentInfo;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneSevenAssignmentInfo;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneSevenAssignmentManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final String FOLDER_PATH = "dna/genes";

    private static Map<String, GeneSevenAssignmentInfo> geneSevenAssignmentInfo = Collections.emptyMap();


    public GeneSevenAssignmentManager() {
        super(GSON, FOLDER_PATH);
        FossilGenCraft.LOGGER.info("Gene Seven Assignment Manager Initialized, scanning folder '{}' ", FOLDER_PATH );
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        FossilGenCraft.LOGGER.info("Applying Gene Assignment data(Processing {} files)...", pObject.size());
        Map<String, GeneSevenAssignmentInfo> newMap = new HashMap<>();

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

                JsonObject dataGeneSlot7 = GsonHelper.getAsJsonObject(jsonObject, "data_gene_slot_7");

                String geneType = GsonHelper.getAsString(dataGeneSlot7, "gene_type");

                Type listType = new TypeToken<List<GeneSevenAssignmentInfo.GeneSevenGoals>>() {}.getType();

                List<GeneSevenAssignmentInfo.GeneSevenGoals> goals = GSON.fromJson(dataGeneSlot7.get("goals"), listType);



                GeneSevenAssignmentInfo info = new GeneSevenAssignmentInfo(
                  speciesRL, genesByIdRl, geneType, goals
                );
                if(newMap.containsKey(entityTypeString)){
                    FossilGenCraft.LOGGER.warn("Duplicate Gene Assignment Definition(over-writing previous)");
                }
                newMap.put(entityTypeString, info);
                FossilGenCraft.LOGGER.debug("Loaded Gene 7 Data for entity {} from file {}", speciesIdString, fileId);

            } catch (Exception e) {
                FossilGenCraft.LOGGER.error("Failed to parse Gene Assignment file: {} - Error: {}", fileId, e.getMessage());
            }


        }
        geneSevenAssignmentInfo = newMap;
        FossilGenCraft.LOGGER.info("Finished applying Gene 7 Assignment Data. Loaded {} valid entries", geneSevenAssignmentInfo.size());

    }
    @Nullable
    public static GeneSevenAssignmentInfo getGeneSevenInfoForEntity(String entityType) {
        return geneSevenAssignmentInfo.get(entityType);
    }
}
