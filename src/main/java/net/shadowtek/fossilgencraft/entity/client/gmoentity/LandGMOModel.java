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


    @Override
    public ResourceLocation getModelResource(GMOLandEntity animatable) {
       String entityId = animatable.getTypeVariant();

        String geneOneAssignment = animatable.gene1SpeciesModelLocation();
        if(geneOneAssignment != null) {
            return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, geneOneAssignment);
        } else {
            String fileLocation = "geo/gmoentity/base/meat.geo.json";
            return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, fileLocation);
        }

    }

    @Override
    public ResourceLocation getTextureResource(GMOLandEntity animatable) {

        if(animatable.gene1SpeciesTextureLocation() != null) {
            String fileLocation = animatable.gene1SpeciesTextureLocation();
            return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, fileLocation);
        }else{
            String fileLocation = "textures/gmoentity/base/meat.png";
            return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, fileLocation);
        }

    }

    @Override
    public ResourceLocation getAnimationResource(GMOLandEntity animatable) {
        String entityId = animatable.getTypeVariant();

        String geneOneAssignment = animatable.gene1SpeciesAnimationLocation();
        if(geneOneAssignment != null) {
            String fileLocation = geneOneAssignment;
            return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, fileLocation);
        } else {
            String fileLocation = "animations/meatcube.walk.json";
            return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, fileLocation);
        }

    }
}
