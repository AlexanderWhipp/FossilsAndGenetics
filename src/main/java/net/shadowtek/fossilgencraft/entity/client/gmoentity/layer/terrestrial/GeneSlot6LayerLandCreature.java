package net.shadowtek.fossilgencraft.entity.client.gmoentity.layer.terrestrial;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneSixAssignmentInfo;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneTwoAssignmentInfo;
import net.shadowtek.fossilgencraft.data.loader.GeneSixAssignmentManager;
import net.shadowtek.fossilgencraft.data.loader.GeneTwoAssignmentManager;
import net.shadowtek.fossilgencraft.entity.custom.gmotypes.GMOLandEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

public class GeneSlot6LayerLandCreature extends GeoRenderLayer<GMOLandEntity> {

    public GeneSlot6LayerLandCreature(GeoRenderer<GMOLandEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack poseStack, GMOLandEntity animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        String geneOneSpecies = animatable.getTypeVariant();
        String geneSixSpecies = animatable.getGene6VariantType();

        GeneSixAssignmentInfo geneSixAssignmentInfo = GeneSixAssignmentManager.getGeneSixInfoForEntity(geneSixSpecies);

        ResourceLocation textureFilePath = ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, geneSixAssignmentInfo.pathToTextureLocation().toString());
        ResourceLocation defaultPath = ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "textures/gmoentity/empty_gene.jpg");
        ResourceLocation activePath;


        if (geneOneSpecies.equals(geneSixSpecies)) {
            activePath = defaultPath;

        } else {
            activePath = textureFilePath;
        }


            RenderType skinLayerRenderType = RenderType.entityDecal(activePath);

            getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, skinLayerRenderType,
                    bufferSource.getBuffer(skinLayerRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY,
                    Color.WHITE.argbInt());
        }

}
