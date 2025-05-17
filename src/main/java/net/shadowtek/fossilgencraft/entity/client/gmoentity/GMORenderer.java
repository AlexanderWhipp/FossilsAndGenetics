package net.shadowtek.fossilgencraft.entity.client.gmoentity;


import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.shadowtek.fossilgencraft.entity.client.gmoentity.layer.GeneSlot2Layer;
import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;
import software.bernie.geckolib.renderer.specialty.DynamicGeoEntityRenderer;


public class GMORenderer extends DynamicGeoEntityRenderer<GMOEntity> {

    public GMORenderer(EntityRendererProvider.Context context) {
        super(context, new GMOModel());

       // addRenderLayer(new GeneSlot2Layer(this));



    }

    @Override
    public void render(GMOEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight){

        poseStack.scale(1f,1f,1f);

        super.render(entity,entityYaw,partialTick,poseStack,bufferSource,packedLight);
    }
}
