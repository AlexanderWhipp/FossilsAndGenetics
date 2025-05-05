package net.shadowtek.fossilgencraft.entity.client.gmoentity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.entity.client.GeneOneVariants;
import net.shadowtek.fossilgencraft.entity.client.gmoentity.layer.GeneSlot2Layer;
import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class GMORenderer extends GeoEntityRenderer<GMOEntity> {
    private static final Map<GeneOneVariants, ResourceLocation> SKIN_LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GeneOneVariants.class), map -> {
                map.put(GeneOneVariants.CHICKEN,
                        ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "textures/gmoentity/base/chicken.png"));
                map.put(GeneOneVariants.FROG,
                        ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "textures/gmoentity/base/frog.png"));
            });
    public GMORenderer(EntityRendererProvider.Context context) {
        super(context, new GMOModel());

        addRenderLayer(new GeneSlot2Layer(this));


    }

    @Override
    public ResourceLocation getTextureLocation(GMOEntity animatable) {
        return SKIN_LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public void render(GMOEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight){

        poseStack.scale(1f,1f,1f);

        super.render(entity,entityYaw,partialTick,poseStack,bufferSource,packedLight);
    }
}
