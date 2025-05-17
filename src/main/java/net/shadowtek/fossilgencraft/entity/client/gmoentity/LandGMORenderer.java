package net.shadowtek.fossilgencraft.entity.client.gmoentity;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneOneAssignmentInfo;
import net.shadowtek.fossilgencraft.data.loader.GeneOneAssignmentManager;

import net.shadowtek.fossilgencraft.entity.client.gmoentity.layer.terrestrial.GeneSlot5LayerLandCreature;
import net.shadowtek.fossilgencraft.entity.client.gmoentity.layer.terrestrial.GeneSlot6LayerLandCreature;
import net.shadowtek.fossilgencraft.entity.client.gmoentity.modelparts.HeadModel;
import net.shadowtek.fossilgencraft.entity.custom.gmotypes.GMOLandEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.loading.json.raw.Bone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.specialty.DynamicGeoEntityRenderer;

import java.util.Map;

public class LandGMORenderer extends GeoEntityRenderer<GMOLandEntity> {

    public LandGMORenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LandGMOModel());
    }

    @Override
    public ResourceLocation getTextureLocation(GMOLandEntity animatable) {
        String geneOneAssignment = animatable.gene1SpeciesTextureLocation();
        if (geneOneAssignment != null){
            return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, geneOneAssignment);

        } else return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "textures/gmoentity/base/meat.png");
    }

    @Override
    public void render(GMOLandEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight){

        poseStack.scale(1f,1f,1f);

        super.render(entity,entityYaw,partialTick,poseStack,bufferSource,packedLight);
    }
}
