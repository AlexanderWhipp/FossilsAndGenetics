package net.shadowtek.fossilgencraft.entity.client.gmoentity;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.shadowtek.fossilgencraft.entity.client.gmoentity.layer.terrestrial.GeneSlot2LayerLandCreature;
import net.shadowtek.fossilgencraft.entity.custom.gmotypes.GMOLandEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class LandGMORenderer extends GeoEntityRenderer<GMOLandEntity> {


    public LandGMORenderer(EntityRendererProvider.Context context) {
        super(context, new LandGMOModel());

    addRenderLayer(new GeneSlot2LayerLandCreature(this));


    }

    @Override
    public void render(GMOLandEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight){

        poseStack.scale(1f,1f,1f);

        super.render(entity,entityYaw,partialTick,poseStack,bufferSource,packedLight);
    }
}
