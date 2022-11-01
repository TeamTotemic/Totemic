package pokefenn.totemic.client.model;

import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.util.Mth;
import pokefenn.totemic.entity.BaldEagle;

public class BaldEagleModel<T extends BaldEagle> extends AgeableListModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;

    public BaldEagleModel(ModelPart root) {
        super(true, 10.0F, 1.0F);
        head = root.getChild("head");
        body = root.getChild("body");
        leftLeg = root.getChild("leftLeg");
        rightLeg = root.getChild("rightLeg");
        leftWing = root.getChild("leftWing");
        rightWing = root.getChild("rightWing");
        tail = root.getChild("tail");
    }

    public static LayerDefinition createLayer() {
        var mesh = new MeshDefinition();
        var root = mesh.getRoot();

        var head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(10, 5)
                .addBox(-1.0F, -1.5F, -1.0F, 2F, 3F, 2F, false),
                PartPose.offset(0.0F, 15.0F, -2.76F));
        head.addOrReplaceChild("headTop", CubeListBuilder.create()
                .texOffs(8, 0)
                .addBox(-1.0F, -0.5F, -2.0F, 2F, 1F, 4F, false),
                PartPose.offset(0.0F, -2.0F, -1.0F));
        head.addOrReplaceChild("mouth", CubeListBuilder.create()
                .texOffs(2, 0)
                .addBox(-0.5F, -1.0F, -0.5F, 2F, 2F, 1F, false),
                PartPose.offset(-0.5F, -0.5F, -1.5F));
        var beak = head.addOrReplaceChild("beak", CubeListBuilder.create()
                .texOffs(0, 3)
                .addBox(-0.5F, 0.1F, -3.3F, 2F, 2F, 3F, false),
                PartPose.offsetAndRotation(-0.5F, -1.65F, -1.65F, 0.0F, 0.00645771823237902F, 0.0F));
        beak.addOrReplaceChild("beakTip", CubeListBuilder.create()
                .texOffs(2, 8)
                .addBox(-0.5F, 1.2F, -3.35F, 2F, 1F, 1F, false),
                PartPose.offset(0.0F, 0.15F, 0.05F));

        root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(8, 10)
                .addBox(-1.5F, 0.0F, -1.5F, 3F, 7F, 3F, false),
                PartPose.offsetAndRotation(0.0F, 16.0F, -3.0F, 0.49375364538919575F, 0.0F, 0.0F));
        root.addOrReplaceChild("leftLeg", CubeListBuilder.create()
                .texOffs(8, 20)
                .addBox(-0.5F, 0.0F, -0.5F, 1F, 2F, 1F, false),
                PartPose.offset(-1.0F, 22.0F, -1.05F));
        root.addOrReplaceChild("rightLeg", CubeListBuilder.create()
                .texOffs(12, 20)
                .addBox(-0.5F, 0.0F, -0.5F, 1F, 2F, 1F, false),
                PartPose.offset(1.0F, 22.0F, -1.05F));
        root.addOrReplaceChild("leftWing", CubeListBuilder.create()
                .texOffs(0, 10)
                .addBox(-0.5F, 0.0F, -1.5F, 1F, 6F, 3F, false),
                PartPose.offsetAndRotation(-1.5F, 16.94F, -2.76F, -0.6981317007977318F, -3.141592653589793F, 0.08726646259971647F));
        root.addOrReplaceChild("rightWing", CubeListBuilder.create()
                .texOffs(20, 10)
                .addBox(-0.5F, 0.0F, -1.5F, 1F, 6F, 3F, false),
                PartPose.offsetAndRotation(1.5F, 16.94F, -2.76F, -0.6981317007977318F, -3.141592653589793F, -0.08726646259971647F));
        root.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(0, 20)
                .addBox(-1.5F, -0.2F, -1.0F, 3F, 5F, 1F, false),
                PartPose.offsetAndRotation(0.0F, 21.07F, 1.16F, 1.083151333787681F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return List.of(head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return List.of(body, leftLeg, rightLeg, leftWing, rightWing, tail);
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        setupAnim(getState(pEntity), pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
    }

    private void setupAnim(State state, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.zRot = 0.0F;
        this.head.x = 0.0F;
        this.body.x = 0.0F;
        this.tail.x = 0.0F;
        this.rightWing.x = -1.5F;
        this.leftWing.x = 1.5F;
        switch(state) {
           case SITTING:
              break;
           case STANDING:
              this.leftLeg.xRot += Mth.cos(pLimbSwing * 0.6662F) * 1.4F * pLimbSwingAmount;
              this.rightLeg.xRot += Mth.cos(pLimbSwing * 0.6662F + (float)Math.PI) * 1.4F * pLimbSwingAmount;
           case FLYING:
           default:
              float f2 = pAgeInTicks * 0.3F;
              this.head.y = 15.69F + f2;
              this.tail.xRot = 1.015F + Mth.cos(pLimbSwing * 0.6662F) * 0.3F * pLimbSwingAmount;
              this.tail.y = 21.07F + f2;
              this.body.y = 16.5F + f2;
              this.leftWing.zRot = -0.0873F - pAgeInTicks;
              this.leftWing.y = 16.94F + f2;
              this.rightWing.zRot = 0.0873F + pAgeInTicks;
              this.rightWing.y = 16.94F + f2;
              this.leftLeg.y = 22.0F + f2;
              this.rightLeg.y = 22.0F + f2;
        }
    }

    @Override
    public void prepareMobModel(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick) {
        prepare(getState(pEntity));
    }

    private void prepare(State state) {
        this.body.xRot = 0.4937F;
        this.leftWing.xRot = -0.6981F;
        this.leftWing.yRot = -(float)Math.PI;
        this.rightWing.xRot = -0.6981F;
        this.rightWing.yRot = -(float)Math.PI;
        this.leftLeg.xRot = -0.0299F;
        this.rightLeg.xRot = -0.0299F;
        this.leftLeg.y = 22.0F;
        this.rightLeg.y = 22.0F;
        this.leftLeg.zRot = 0.0F;
        this.rightLeg.zRot = 0.0F;
        switch(state) {
           case SITTING:
              this.head.y = 17.59F;
              this.tail.xRot = 1.5388988F;
              this.tail.y = 22.97F;
              this.body.y = 18.4F;
              this.leftWing.zRot = -0.0873F;
              this.leftWing.y = 18.84F;
              this.rightWing.zRot = 0.0873F;
              this.rightWing.y = 18.84F;
              ++this.leftLeg.y;
              ++this.rightLeg.y;
              ++this.leftLeg.xRot;
              ++this.rightLeg.xRot;
              break;
           case STANDING:
           default:
              break;
           case FLYING:
              this.leftLeg.xRot += 0.6981317F;
              this.rightLeg.xRot += 0.6981317F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack ps, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        ps.pushPose();
        ps.translate(0F, -0.75F, 0F);
        ps.scale(1.5F, 1.5F, 1.5F);
        super.renderToBuffer(ps, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        ps.popPose();
    }

    private State getState(T entity) {
        if(entity.isInSittingPose())
            return State.SITTING;
        else
            return entity.isFlying() ? State.FLYING : State.STANDING;
    }

    private static enum State {
        FLYING, STANDING, SITTING
    }
}
