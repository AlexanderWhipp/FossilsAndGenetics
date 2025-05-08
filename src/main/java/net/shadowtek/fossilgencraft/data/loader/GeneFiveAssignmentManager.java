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
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneFiveAssignmentInfo;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneTwoAssignmentInfo;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GeneFiveAssignmentManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final String FOLDER_PATH = "dna/genes";

    private static Map<String, GeneFiveAssignmentInfo> geneFiveDataInfoMap = Collections.emptyMap();


    public GeneFiveAssignmentManager() {
        super(GSON, FOLDER_PATH);
        FossilGenCraft.LOGGER.info("Gene One Assignment Manager Initialized, scanning folder '{}' ", FOLDER_PATH );
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        FossilGenCraft.LOGGER.info("Applying Gene Assignment data(Processing {} files)...", pObject.size());
        Map<String, GeneFiveAssignmentInfo> newMap = new HashMap<>();

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

                JsonObject dataGeneSlot5 = GsonHelper.getAsJsonObject(jsonObject, "data_gene_slot_5");

                String geneType = GsonHelper.getAsString(dataGeneSlot5, "gene_type");
                int geneSpeciesIdNumber = GsonHelper.getAsInt(dataGeneSlot5, "gene_id");
                String layer5textureLocation = GsonHelper.getAsString(dataGeneSlot5, "path_to_texture");
                ResourceLocation geneFiveRL = ResourceLocation.parse(layer5textureLocation);



                GeneFiveAssignmentInfo info = new GeneFiveAssignmentInfo(
                  speciesRL, genesByIdRl, geneType, geneSpeciesIdNumber, geneFiveRL
                );
                if(newMap.containsKey(entityTypeString)){
                    FossilGenCraft.LOGGER.warn("Duplicate Gene Assignment Definition(over-writing previous)");
                }
                newMap.put(entityTypeString, info);
                FossilGenCraft.LOGGER.debug("Loaded Gene 5 Data for entity {} from file {}", speciesIdString, fileId);

            } catch (Exception e) {
                FossilGenCraft.LOGGER.error("Failed to parse Gene Assignment file: {} - Error: {}", fileId, e.getMessage());
            }


        }
        geneFiveDataInfoMap = newMap;
        FossilGenCraft.LOGGER.info("Finished applying Gene 5 Assignment Data. Loaded {} valid entries", geneFiveDataInfoMap.size());

    }
    @Nullable
    public static GeneFiveAssignmentInfo getGeneFiveInfoForEntity(String entityType) {
        return geneFiveDataInfoMap.get(entityType);
    }
}
