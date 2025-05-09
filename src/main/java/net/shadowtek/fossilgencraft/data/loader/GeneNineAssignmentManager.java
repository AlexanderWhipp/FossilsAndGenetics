package net.shadowtek.fossilgencraft.data.loader;

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
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneSixAssignmentInfo;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GeneNineAssignmentManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final String FOLDER_PATH = "dna/genes";

    private static Map<String, GeneNineAssignmentInfo> geneNineAssignmentInfo = Collections.emptyMap();


    public GeneNineAssignmentManager() {
        super(GSON, FOLDER_PATH);
        FossilGenCraft.LOGGER.info("Gene Nine Assignment Manager Initialized, scanning folder '{}' ", FOLDER_PATH );
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        FossilGenCraft.LOGGER.info("Applying Gene Assignment data(Processing {} files)...", pObject.size());
        Map<String, GeneNineAssignmentInfo> newMap = new HashMap<>();

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

                JsonObject dataGeneSlot9 = GsonHelper.getAsJsonObject(jsonObject, "data_gene_slot_9");

                String geneType = GsonHelper.getAsString(dataGeneSlot9, "gene_type");
                boolean canherd = GsonHelper.getAsBoolean(dataGeneSlot9, "herd");


                GeneNineAssignmentInfo info = new GeneNineAssignmentInfo(
                  speciesRL, genesByIdRl, geneType, canherd
                );
                if(newMap.containsKey(entityTypeString)){
                    FossilGenCraft.LOGGER.warn("Duplicate Gene Assignment Definition(over-writing previous)");
                }
                newMap.put(entityTypeString, info);
                FossilGenCraft.LOGGER.debug("Loaded Gene 6 Data for entity {} from file {}", speciesIdString, fileId);

            } catch (Exception e) {
                FossilGenCraft.LOGGER.error("Failed to parse Gene Assignment file: {} - Error: {}", fileId, e.getMessage());
            }


        }
        geneNineAssignmentInfo = newMap;
        FossilGenCraft.LOGGER.info("Finished applying Gene 6 Assignment Data. Loaded {} valid entries", geneNineAssignmentInfo.size());

    }
    @Nullable
    public static GeneNineAssignmentInfo getGeneNineInfoForEntity(String entityType) {
        return geneNineAssignmentInfo.get(entityType);
    }
}
