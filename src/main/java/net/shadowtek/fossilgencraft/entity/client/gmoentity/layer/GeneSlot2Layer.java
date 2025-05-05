package net.shadowtek.fossilgencraft.entity.client.gmoentity.layer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.entity.client.GeneOneVariants;
import net.shadowtek.fossilgencraft.entity.client.GeneTwoVariants;
import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

import java.util.Map;

public class GeneSlot2Layer extends GeoRenderLayer<GMOEntity> {
    private static final Map<GeneOneVariants, ResourceLocation> TEXTURE_LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GeneOneVariants.class), map -> {
                map.put(GeneOneVariants.CHICKEN,
                        ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "textures/gmoentity/genetwo/chicken.png"));
                map.put(GeneOneVariants.FROG,
                        ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "textures/gmoentity/genetwo/frog.png"));
            });




    public GeneSlot2Layer(GeoRenderer<GMOEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, GMOEntity animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType skinLayerRenderType = RenderType.entityDecal(TEXTURE_LOCATION_BY_VARIANT.get(animatable.getGeneTwoVariant()));

        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, skinLayerRenderType,
                bufferSource.getBuffer(skinLayerRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                Color.WHITE.argbInt());
    }
}
