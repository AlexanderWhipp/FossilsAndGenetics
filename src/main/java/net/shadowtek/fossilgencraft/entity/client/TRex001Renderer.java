package net.shadowtek.fossilgencraft.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.entity.custom.TRex001Entity;

public class TRex001Renderer extends MobRenderer<TRex001Entity, TRex001Model<TRex001Entity>> {

    public TRex001Renderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TRex001Model<>(pContext.bakeLayer(TRex001Model.LAYER_LOCATION)), 0.85f);
    }

    @Override
    public ResourceLocation getTextureLocation(TRex001Entity pEntity) {
        return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "textures/entity/trex/trex001.png");
    }

    @Override
    public void render(TRex001Entity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        if(pEntity.isBaby()) {
            pPoseStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            pPoseStack.scale(1f,1f,1f);
        }
        pPoseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));


        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);

    }




}
