package pokefenn.totemic.client.model;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import pokefenn.totemic.entity.Baykok;

public class BaykokModel<T extends Baykok> extends HumanoidModel<T> {
    public BaykokModel(ModelPart pRoot) {
        super(pRoot);
    }

    public static LayerDefinition createLayer(CubeDeformation deformation) {
        var mesh = HumanoidModel.createMesh(deformation, 0);
        var root = mesh.getRoot();

        root.addOrReplaceChild("left_arm", CubeListBuilder.create()
                .texOffs(42, 0)
                .addBox(-2F, -2F, -0.5F, 3F, 13F, 3F, true),
                PartPose.offset(5F, 2F, 0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
                .texOffs(42, 16)
                .addBox(-1F, 0F, -1.5F, 3F, 12F, 3F, true),
                PartPose.offset(2F, 12F, 0F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create()
                .texOffs(42, 0)
                .addBox(-1F, -2F, -0.5F, 3F, 13F, 3F, true),
                PartPose.offset(-5F, 2F, 0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
                .texOffs(42, 16)
                .addBox(-2F, 0F, -1.5F, 3F, 12F, 3F, true),
                PartPose.offset(-2F, 12F, 0F));
        root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4F, -8F, -4F, 8F, 8F, 8F, true),
                PartPose.offset(0F, -1F, 0F));

        var hat = root.addOrReplaceChild("hat", CubeListBuilder.create()
                .texOffs(0, 0),
                PartPose.offset(0F, -1F, 0F));
        hat.addOrReplaceChild("headdress1", CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-0.5F, -14F, -1F, 2F, 6F, 2F, true),
                PartPose.offsetAndRotation(0F, 0F, 0F, -0.1745329F, 0F, -0.4712389F));
        hat.addOrReplaceChild("headdress2", CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-1F, -14F, -1F, 2F, 6F, 2F, true),
                PartPose.offsetAndRotation(0F, 0F, 0F, -0.1745329F, 0F, -0.2268928F));
        hat.addOrReplaceChild("headdress3", CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-1F, -14F, -1F, 2F, 6F, 2F, true),
                PartPose.offsetAndRotation(0F, 0F, 0F, -0.1745329F, 0F, 0F));
        hat.addOrReplaceChild("headdress4", CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-1F, -14F, -1F, 2F, 6F, 2F, true),
                PartPose.offsetAndRotation(0F, 0F, 0F, -0.1745329F, 0F, 0.2268928F));
        hat.addOrReplaceChild("headdress5", CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-1.5F, -14F, -1F, 2F, 6F, 2F, true),
                PartPose.offsetAndRotation(0F, 0F, 0F, -0.1745329F, 0F, 0.4712389F));

        var body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0),
                PartPose.ZERO);
        body.addOrReplaceChild("pelvis", CubeListBuilder.create()
                .texOffs(0, 37)
                .addBox(-4F, 11F, -2F, 8F, 1F, 4F, true),
                PartPose.ZERO);
        body.addOrReplaceChild("quiver", CubeListBuilder.create()
                .texOffs(24, 20)
                .addBox(-5F, -2F, 2F, 4F, 11F, 2F, true),
                PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, -0.5235988F));
        body.addOrReplaceChild("ribs", CubeListBuilder.create()
                .texOffs(0, 16)
                .addBox(-3F, -1F, -2F, 6F, 8F, 4F, true),
                PartPose.ZERO);
        body.addOrReplaceChild("spine", CubeListBuilder.create()
                .texOffs(0, 28)
                .addBox(-1.5F, 7F, -2F, 3F, 5F, 4F, true),
                PartPose.ZERO);
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        rightArmPose = ArmPose.BOW_AND_ARROW;
        super.prepareMobModel(pEntity, pLimbSwing, pLimbSwingAmount, pPartialTick);
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
        leftArm.xRot = -Mth.HALF_PI;
        leftArm.yRot = 0.35F;
        rightArm.xRot = -Mth.HALF_PI;
        rightArm.yRot = -0.1F;
    }

    @Override
    public void translateToHand(HumanoidArm pSide, PoseStack pPoseStack) {
        super.translateToHand(pSide, pPoseStack);
        if(pSide == HumanoidArm.RIGHT)
            pPoseStack.translate(0.04F, 0.0F, 0.085F);
        else
            pPoseStack.translate(-0.04F, 0.0F, 0.085F);
    }
}
