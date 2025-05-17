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
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneTenAssignmentInfo;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GeneTenAssignmentManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final String FOLDER_PATH = "dna/genes";

    private static Map<String, GeneTenAssignmentInfo> geneTenAssignmentInfo = Collections.emptyMap();


    public GeneTenAssignmentManager() {
        super(GSON, FOLDER_PATH);
        FossilGenCraft.LOGGER.info("Gene Ten Assignment Manager Initialized, scanning folder '{}' ", FOLDER_PATH );
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        FossilGenCraft.LOGGER.info("Applying Gene Assignment data(Processing {} files)...", pObject.size());
        Map<String, GeneTenAssignmentInfo> newMap = new HashMap<>();

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

                JsonObject dataGeneSlot10 = GsonHelper.getAsJsonObject(jsonObject, "data_gene_slot_10");

                String geneType = GsonHelper.getAsString(dataGeneSlot10, "gene_type");
                boolean isAgressive = GsonHelper.getAsBoolean(dataGeneSlot10, "is_aggressive");
                boolean isTameable = GsonHelper.getAsBoolean(dataGeneSlot10, "is_tameable");
                boolean isRideable = GsonHelper.getAsBoolean(dataGeneSlot10, "is_rideable");
                boolean defendOwner = GsonHelper.getAsBoolean(dataGeneSlot10, "attack_owner_attacker");


                GeneTenAssignmentInfo info = new GeneTenAssignmentInfo(
                  speciesRL, genesByIdRl, geneType, isAgressive, isTameable, isRideable, defendOwner
                );
                if(newMap.containsKey(entityTypeString)){
                    FossilGenCraft.LOGGER.warn("Duplicate Gene Assignment Definition(over-writing previous)");
                }
                newMap.put(entityTypeString, info);
                FossilGenCraft.LOGGER.debug("Loaded Gene 10 Data for entity {} from file {}", speciesIdString, fileId);

            } catch (Exception e) {
                FossilGenCraft.LOGGER.error("Failed to parse Gene Assignment file: {} - Error: {}", fileId, e.getMessage());
            }


        }
        geneTenAssignmentInfo = newMap;
        FossilGenCraft.LOGGER.info("Finished applying Gene 10 Assignment Data. Loaded {} valid entries", geneTenAssignmentInfo.size());

    }
    @Nullable
    public static GeneTenAssignmentInfo getGeneTenInfoForEntity(String entityType) {
        return geneTenAssignmentInfo.get(entityType);
    }
}
