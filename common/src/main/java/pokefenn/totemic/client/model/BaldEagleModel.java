package pokefenn.totemic.client.model;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import pokefenn.totemic.client.model.BaldEagleModel.BaldEagleRenderState;
import pokefenn.totemic.entity.BaldEagle;

public class BaldEagleModel extends EntityModel<BaldEagleRenderState> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart leftWing;
    private final ModelPart rightWing;
    private final ModelPart tail;

    public BaldEagleModel(ModelPart root) {
        super(root);
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
    public void setupAnim(BaldEagleRenderState state) {
        super.setupAnim(state);
        prepare(state.pose);
        this.head.xRot = state.xRot * ((float)Math.PI / 180F);
        this.head.yRot = state.yRot * ((float)Math.PI / 180F);
        this.head.zRot = 0.0F;
        this.head.x = 0.0F;
        this.body.x = 0.0F;
        this.tail.x = 0.0F;
        this.rightWing.x = -1.5F;
        this.leftWing.x = 1.5F;
        switch(state.pose) {
            case SITTING:
                break;
            case STANDING:
                this.leftLeg.xRot += Mth.cos(state.walkAnimationPos * 0.6662F) * 1.4F * state.walkAnimationSpeed;
                this.rightLeg.xRot += Mth.cos(state.walkAnimationPos * 0.6662F + (float)Math.PI) * 1.4F * state.walkAnimationSpeed;
            case FLYING:
            default:
                float bobbingBody = state.flapAngle * 0.3F;
                this.head.y = 15.69F + bobbingBody;
                this.tail.xRot = 1.015F + Mth.cos(state.walkAnimationPos * 0.6662F) * 0.3F * state.walkAnimationSpeed;
                this.tail.y = 21.07F + bobbingBody;
                this.body.y = 16.5F + bobbingBody;
                this.leftWing.zRot = -0.0873F - state.flapAngle;
                this.leftWing.y = 16.94F + bobbingBody;
                this.rightWing.zRot = 0.0873F + state.flapAngle;
                this.rightWing.y = 16.94F + bobbingBody;
                this.leftLeg.y = 22.0F + bobbingBody;
                this.rightLeg.y = 22.0F + bobbingBody;
        }
    }

    private void prepare(Pose pose) {
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
        switch(pose) {
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

    public static Pose getPose(BaldEagle entity) {
        if(entity.isInSittingPose())
            return Pose.SITTING;
        else
            return entity.isFlying() ? Pose.FLYING : Pose.STANDING;
    }

    public static enum Pose {
        FLYING, STANDING, SITTING
    }

    public static class BaldEagleRenderState extends LivingEntityRenderState {
        public float flapAngle;
        public Pose pose = Pose.FLYING;
    }
}
