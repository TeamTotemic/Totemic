package pokefenn.totemic.client.model;

import java.util.List;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import pokefenn.totemic.entity.BaldEagle;

public class BaldEagleModel<T extends BaldEagle> extends AgeableListModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public BaldEagleModel(ModelPart root) {
        head = root.getChild("head");
        body = root.getChild("body");
        leftWing = root.getChild("leftWing");
        rightWing = root.getChild("rightWing");
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

        var body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.ZERO);
        body.addOrReplaceChild("leftLeg", CubeListBuilder.create()
                .texOffs(8, 20)
                .addBox(-0.5F, 0.0F, -0.5F, 1F, 2F, 1F, false),
                PartPose.offset(-1.0F, 22.0F, -1.05F));
        body.addOrReplaceChild("rightLeg", CubeListBuilder.create()
                .texOffs(12, 20)
                .addBox(-0.5F, 0.0F, -0.5F, 1F, 2F, 1F, false),
                PartPose.offset(1.0F, 22.0F, -1.05F));
        body.addOrReplaceChild("tailFeathers", CubeListBuilder.create()
                .texOffs(0, 20)
                .addBox(-1.5F, -0.2F, -1.0F, 3F, 5F, 1F, false),
                PartPose.offsetAndRotation(0.0F, 21.07F, 1.16F, 1.083151333787681F, 0.0F, 0.0F));
        body.addOrReplaceChild("torso", CubeListBuilder.create()
                .texOffs(8, 10)
                .addBox(-1.5F, 0.0F, -1.5F, 3F, 7F, 3F, false),
                PartPose.offsetAndRotation(0.0F, 16.0F, -3.0F, 0.49375364538919575F, 0.0F, 0.0F));

        root.addOrReplaceChild("leftWing", CubeListBuilder.create()
                .texOffs(0, 10)
                .addBox(-0.5F, 0.0F, -1.5F, 1F, 6F, 3F, false),
                PartPose.offsetAndRotation(-1.5F, 16.94F, -2.76F, -0.6981317007977318F, -3.141592653589793F, 0.08726646259971647F));
        root.addOrReplaceChild("rightWing", CubeListBuilder.create()
                .texOffs(20, 10)
                .addBox(-0.5F, 0.0F, -1.5F, 1F, 6F, 3F, false),
                PartPose.offsetAndRotation(1.5F, 16.94F, -2.76F, -0.6981317007977318F, -3.141592653589793F, -0.08726646259971647F));
        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return List.of(head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return List.of(body, leftWing, rightWing);
    }

    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        // TODO Auto-generated method stub

    }

}
