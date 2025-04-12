package net.shadowtek.fossilgencraft.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.entity.custom.TRex001Entity;

public class TRex001Model<T extends TRex001Entity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "trexentity"), "main");
    private final ModelPart torso;
    private final ModelPart skull;


    public TRex001Model(ModelPart root) {
        this.torso = root.getChild("body").getChild("torso");
        this.skull = torso.getChild("spine").getChild("head_and_kneck").getChild("kneck").getChild("skull");
    }


    public static LayerDefinition createBodyLayer() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-0.5F, 0.0F, -2.5F));

            PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.0F, -2.5F));

            PartDefinition right_leg = torso.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(3.75F, 6.0F, 3.5F));

            PartDefinition right_thigh = right_leg.addOrReplaceChild("right_thigh", CubeListBuilder.create(), PartPose.offset(-5.0F, -6.0F, 0.0F));

            PartDefinition right_thigh_r1 = right_thigh.addOrReplaceChild("right_thigh_r1", CubeListBuilder.create().texOffs(0, 34).addBox(-4.7403F, -7.2961F, -2.0F, 5.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.7403F, 5.2961F, -5.9983F, 0.0F, 0.0F, -0.6545F));

            PartDefinition right_calf = right_thigh.addOrReplaceChild("right_calf", CubeListBuilder.create(), PartPose.offset(5.7403F, 5.2961F, 0.0017F));

            PartDefinition right_calf_r1 = right_calf.addOrReplaceChild("right_calf_r1", CubeListBuilder.create().texOffs(30, 38).addBox(-1.7403F, -4.2961F, -2.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 4.0F, -6.0F, 0.0F, 0.0F, 0.6109F));

            PartDefinition right_ankle = right_calf.addOrReplaceChild("right_ankle", CubeListBuilder.create(), PartPose.offset(-4.0F, 4.0F, 0.0F));

            PartDefinition right_ankle_r1 = right_ankle.addOrReplaceChild("right_ankle_r1", CubeListBuilder.create().texOffs(16, 38).addBox(-3.0F, -1.0F, -2.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2597F, 2.7039F, -6.0017F, 0.0F, 0.0F, 1.0036F));

            PartDefinition right_toe = right_ankle.addOrReplaceChild("right_toe", CubeListBuilder.create().texOffs(16, 42).addBox(-0.7403F, 0.7039F, -8.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 2.0F, 0.0F));

            PartDefinition left_leg = torso.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(3.75F, 6.0F, 3.5F));

            PartDefinition left_thigh = left_leg.addOrReplaceChild("left_thigh", CubeListBuilder.create(), PartPose.offset(-5.0F, -4.0F, -1.0F));

            PartDefinition left_thigh_r1 = left_thigh.addOrReplaceChild("left_thigh_r1", CubeListBuilder.create().texOffs(22, 27).addBox(-4.0F, -8.0F, -2.0F, 5.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 4.0F, 1.0F, 0.0F, 0.0F, -0.6545F));

            PartDefinition left_calf = left_thigh.addOrReplaceChild("left_calf", CubeListBuilder.create(), PartPose.offset(5.0F, 4.0F, 1.0F));

            PartDefinition left_calf_r1 = left_calf.addOrReplaceChild("left_calf_r1", CubeListBuilder.create().texOffs(38, 27).addBox(-1.0F, -5.0F, -2.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.6109F));

            PartDefinition left_ankle = left_calf.addOrReplaceChild("left_ankle", CubeListBuilder.create(), PartPose.offset(-4.0F, 3.0F, 0.0F));

            PartDefinition left_ankle_r1 = left_ankle.addOrReplaceChild("left_ankle_r1", CubeListBuilder.create().texOffs(16, 38).addBox(-3.0F, -1.0F, -2.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, 0.0F, 0.0F, 0.0F, 1.0036F));

            PartDefinition left_toe = left_ankle.addOrReplaceChild("left_toe", CubeListBuilder.create().texOffs(40, 39).addBox(0.0F, 0.0F, -2.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 3.0F, 0.0F));

            PartDefinition spine = torso.addOrReplaceChild("spine", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition Torso_r1 = spine.addOrReplaceChild("Torso_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -6.0F, -2.0F, 12.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.75F, 4.0F, -1.5F, 0.0F, 0.0F, -0.1745F));

            PartDefinition head_and_kneck = spine.addOrReplaceChild("head_and_kneck", CubeListBuilder.create(), PartPose.offset(6.75F, 4.0F, -1.5F));

            PartDefinition kneck = head_and_kneck.addOrReplaceChild("kneck", CubeListBuilder.create(), PartPose.offset(-0.5F, -2.5F, 1.5F));

            PartDefinition neck_r1 = kneck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(24, 21).addBox(-5.0F, -3.0F, -2.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.4998F, -0.5F, 0.4949F, 0.0F, 0.0F, -0.6109F));

            PartDefinition skull = kneck.addOrReplaceChild("skull", CubeListBuilder.create(), PartPose.offset(3.7498F, -4.0F, -0.0051F));

            PartDefinition skull_r1 = skull.addOrReplaceChild("skull_r1", CubeListBuilder.create().texOffs(0, 24).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 0.5F, 0.5F, 0.0F, 0.0F, 0.48F));

            PartDefinition mouth = skull.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(-3.25F, 6.5F, -1.5F));

            PartDefinition mouth_r1 = mouth.addOrReplaceChild("mouth_r1", CubeListBuilder.create().texOffs(38, 0).addBox(-3.0F, -2.0F, -2.0F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -4.0F, 2.0F, 0.0F, 0.0F, 0.5236F));

            PartDefinition tail = spine.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(6.75F, 4.0F, -1.5F));

            PartDefinition tail_base = tail.addOrReplaceChild("tail_base", CubeListBuilder.create(), PartPose.offset(-11.0F, -1.0F, 1.5F));

            PartDefinition tail_base_r1 = tail_base.addOrReplaceChild("tail_base_r1", CubeListBuilder.create().texOffs(0, 13).addBox(-4.0F, -3.0F, -4.0F, 5.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.5F, 0.0F, 0.0F, -0.3491F));

            PartDefinition tail_middle_1 = tail_base.addOrReplaceChild("tail_middle_1", CubeListBuilder.create(), PartPose.offset(-4.0F, 1.5F, 0.0F));

            PartDefinition tail_middle_1_r1 = tail_middle_1.addOrReplaceChild("tail_middle_1_r1", CubeListBuilder.create().texOffs(24, 13).addBox(-4.0F, -2.0F, -3.0F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.5F, 0.5F, 0.0F, 0.0F, -0.1745F));

            PartDefinition tail_middle_2 = tail_middle_1.addOrReplaceChild("tail_middle_2", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.5F, 0.0F));

            PartDefinition tail_middle_2_r1 = tail_middle_2.addOrReplaceChild("tail_middle_2_r1", CubeListBuilder.create().texOffs(38, 7).addBox(-4.0F, -2.0F, -2.0F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 0.5F, 0.0F, 0.0F, -0.0436F));

            PartDefinition tail_tip = tail_middle_2.addOrReplaceChild("tail_tip", CubeListBuilder.create(), PartPose.offset(-5.75F, -1.0F, 0.0F));

            PartDefinition tail_tip_r1 = tail_tip.addOrReplaceChild("tail_tip_r1", CubeListBuilder.create().texOffs(40, 43).addBox(-4.0F, -1.0F, -1.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 1.0F, 0.5F, 0.0F, 0.0F, 0.2182F));

            PartDefinition left_arm = spine.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(6.75F, 4.0F, -1.5F));

            PartDefinition left_arm_r1 = left_arm.addOrReplaceChild("left_arm_r1", CubeListBuilder.create().texOffs(0, 45).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 4.0F, 0.0F, 0.0F, 0.9599F));

            PartDefinition right_arm = spine.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(6.75F, 4.0F, -1.5F));

            PartDefinition right_arm_r1 = right_arm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(8, 45).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.9599F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

    @Override
    public void setupAnim(TRex001Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);

        this.animateWalk(TRex001Animations.ANIM_TREX_WALKING, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, TRex001Animations.ANIM_TREX_IDLE, ageInTicks, 1f);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch) {
        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);

        this.skull.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.skull.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        torso.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return torso;
    }
}