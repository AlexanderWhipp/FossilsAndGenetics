package net.shadowtek.fossilgencraft.entity.client.gmoentity;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneOneAssignmentInfo;
import net.shadowtek.fossilgencraft.data.loader.GeneOneAssignmentManager;
import net.shadowtek.fossilgencraft.entity.client.GeneOneVariants;
import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;
import net.shadowtek.fossilgencraft.entity.custom.gmotypes.GMOLandEntity;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
import software.bernie.geckolib.model.GeoModel;

import java.util.Map;

public class LandGMOModel extends GeoModel<GMOLandEntity> {

    private static final Map<GeneOneVariants, String> GET_VARIANT_FOR_ASSIGNMENT =
            Util.make(Maps.newEnumMap(GeneOneVariants.class), map -> {
                map.put(GeneOneVariants.CHICKEN, "minecraft:chicken");
                map.put(GeneOneVariants.FROG, "minecraft:frog");

            });
    @Override
    public ResourceLocation getModelResource(GMOLandEntity animatable) {
        String entityId = GET_VARIANT_FOR_ASSIGNMENT.get(animatable.getVariant());

        GeneOneAssignmentInfo geneOneAssignment = GeneOneAssignmentManager.getGeneInfoForEntity(entityId);
        String fileLocation = geneOneAssignment.modelLocation();

        return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, fileLocation);
    }

    @Override
    public ResourceLocation getTextureResource(GMOLandEntity animatable) {
        String entityId = GET_VARIANT_FOR_ASSIGNMENT.get(animatable.getVariant());

        GeneOneAssignmentInfo geneOneAssignment = GeneOneAssignmentManager.getGeneInfoForEntity(entityId);
        String fileLocation = geneOneAssignment.baseTextureLocation();

        return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, fileLocation);
    }

    @Override
    public ResourceLocation getAnimationResource(GMOLandEntity animatable) {
        String entityId = GET_VARIANT_FOR_ASSIGNMENT.get(animatable.getVariant());

        GeneOneAssignmentInfo geneOneAssignment = GeneOneAssignmentManager.getGeneInfoForEntity(entityId);
        String fileLocation = geneOneAssignment.walkAnimationLocation();

        return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, fileLocation);
    }
}
