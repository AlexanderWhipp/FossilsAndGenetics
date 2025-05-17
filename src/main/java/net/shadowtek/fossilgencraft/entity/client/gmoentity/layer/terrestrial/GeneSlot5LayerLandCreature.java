package net.shadowtek.fossilgencraft.entity.client.gmoentity.layer.terrestrial;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneFiveAssignmentInfo;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneTwoAssignmentInfo;
import net.shadowtek.fossilgencraft.data.loader.GeneFiveAssignmentManager;
import net.shadowtek.fossilgencraft.data.loader.GeneTwoAssignmentManager;
import net.shadowtek.fossilgencraft.entity.custom.gmotypes.GMOLandEntity;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.FastBoneFilterGeoLayer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.Color;

import java.util.List;

public class GeneSlot5LayerLandCreature extends FastBoneFilterGeoLayer<GMOLandEntity> {

    public GeneSlot5LayerLandCreature(GeoRenderer<GMOLandEntity> renderer) {
        super(renderer);
        getAffectedBones().add("body");
        getAffectedBones().add("head");
    }

    @Override
    public void preRender(PoseStack poseStack, GMOLandEntity animatable, BakedGeoModel bakedModel, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        super.preRender(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
    }
}
