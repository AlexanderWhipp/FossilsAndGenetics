package net.shadowtek.fossilgencraft.entity.client.gmoentity.layer.terrestrial;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.entity.custom.gmotypes.GMOLandEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.FastBoneFilterGeoLayer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

import java.util.List;

public class GeneSlot2LayerLandCreature extends FastBoneFilterGeoLayer<GMOLandEntity> {

    public GeneSlot2LayerLandCreature(GeoRenderer<GMOLandEntity> entityRendererIn) {
        super(entityRendererIn);
    }
    ResourceLocation textureFilePath;

    @Override
    protected List<String> getAffectedBones() {
        return super.getAffectedBones();
    }

    @Override
    public void preRender(PoseStack poseStack, GMOLandEntity animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        super.preRender(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
    }


    /*
    @Override
    public void render(PoseStack poseStack, GMOLandEntity animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        String geneOneSpecies = animatable.getTypeVariant();
        String geneTwoSpecies = animatable.getGene2VariantType();

        ResourceLocation geneTwoAssignmentInfo = animatable.gene2SpeciesHeadTextureLocation();
        if (geneTwoAssignmentInfo != null) {
            textureFilePath = geneTwoAssignmentInfo;
        }
        ResourceLocation defaultPath = ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "textures/gmoentity/emptygene.jpg");
        ResourceLocation activePath;

        if (geneOneSpecies.equals(geneTwoSpecies) || (geneTwoAssignmentInfo == null)) {
            activePath = defaultPath;

        } else {
            activePath = textureFilePath;
        }

            RenderType skinLayerRenderType = RenderType.entityTranslucent(activePath);

            getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, skinLayerRenderType,
                    bufferSource.getBuffer(skinLayerRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    Color.WHITE.argbInt());
        }

*/

}
