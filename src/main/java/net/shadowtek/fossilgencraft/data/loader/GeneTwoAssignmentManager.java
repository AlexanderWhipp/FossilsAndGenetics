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
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneTwoAssignmentInfo;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneTwoAssignmentManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final String FOLDER_PATH = "dna/genes";

    private static Map<String, GeneTwoAssignmentInfo> geneTwoDataInfoMap = Collections.emptyMap();


    public GeneTwoAssignmentManager() {
        super(GSON, FOLDER_PATH);
        FossilGenCraft.LOGGER.info("Gene One Assignment Manager Initialized, scanning folder '{}' ", FOLDER_PATH );
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        FossilGenCraft.LOGGER.info("Applying Gene Assignment data(Processing {} files)...", pObject.size());
        Map<String, GeneTwoAssignmentInfo> newMap = new HashMap<>();

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

                JsonObject dataGeneSlot2 = GsonHelper.getAsJsonObject(jsonObject, "data_gene_slot_2");

                String geneType = GsonHelper.getAsString(dataGeneSlot2, "gene_type");
                int geneSpeciesIdNumber = GsonHelper.getAsInt(dataGeneSlot2, "gene_id");
                String layer2textureLocation = GsonHelper.getAsString(dataGeneSlot2, "path_to_texture");
                ResourceLocation geneTwoRL = ResourceLocation.parse(layer2textureLocation);
                String layer2modelLocation = GsonHelper.getAsString(dataGeneSlot2, "path_to_model");
                ResourceLocation headRl = ResourceLocation.parse(layer2modelLocation);


                GeneTwoAssignmentInfo info = new GeneTwoAssignmentInfo(
                  speciesRL, genesByIdRl, geneType, geneSpeciesIdNumber, geneTwoRL, headRl
                );
                if(newMap.containsKey(entityTypeString)){
                    FossilGenCraft.LOGGER.warn("Duplicate Gene Assignment Definition(over-writing previous)");
                }
                newMap.put(entityTypeString, info);
                FossilGenCraft.LOGGER.debug("Loaded Gene 1 Data for entity {} from file {}", speciesIdString, fileId);

            } catch (Exception e) {
                FossilGenCraft.LOGGER.error("Failed to parse Gene Assignment file: {} - Error: {}", fileId, e.getMessage());
            }


        }
        geneTwoDataInfoMap = newMap;
        FossilGenCraft.LOGGER.info("Finished applying Gene 1 Assignment Data. Loaded {} valid entries", geneTwoDataInfoMap.size());

    }
    @Nullable
    public static GeneTwoAssignmentInfo getGeneTwoInfoForEntity(String entityType) {
        return geneTwoDataInfoMap.get(entityType);
    }
}
