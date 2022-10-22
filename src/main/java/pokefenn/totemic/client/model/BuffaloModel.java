package pokefenn.totemic.client.model;

import java.util.List;

import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.util.Mth;
import pokefenn.totemic.entity.Buffalo;

public class BuffaloModel<T extends Buffalo> extends AgeableListModel<T> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;

    public BuffaloModel(ModelPart root) {
        super(true, 10.0F, 2.0F);
        head = root.getChild("head");
        body = root.getChild("body");
        tail = root.getChild("tail");
        leg1 = root.getChild("leg1");
        leg2 = root.getChild("leg2");
        leg3 = root.getChild("leg3");
        leg4 = root.getChild("leg4");
    }

    public static LayerDefinition createLayer() {
        var mesh = new MeshDefinition();
        var root = mesh.getRoot();

        var head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 45)
                .addBox(-4.5F, -7F, -7F, 9F, 7F, 9F, true),
                PartPose.offsetAndRotation(0F, 6F, -7F, Mth.HALF_PI, 0F, 0F));
        head.addOrReplaceChild("hornbase1", CubeListBuilder.create()
                .texOffs(52, 0)
                .addBox(-7.5F, -4F, -1F, 4F, 2F, 2F, true),
                PartPose.ZERO);
        head.addOrReplaceChild("hornbase2", CubeListBuilder.create()
                .texOffs(52, 0)
                .addBox(3.5F, -4F, -1F, 4F, 2F, 2F, false),
                PartPose.ZERO);
        head.addOrReplaceChild("horn1", CubeListBuilder.create()
                .texOffs(52, 4)
                .addBox(6.5F, -4F, 0F, 2F, 2F, 4F, false),
                PartPose.ZERO);
        head.addOrReplaceChild("horn2", CubeListBuilder.create()
                .texOffs(52, 10)
                .addBox(5.5F, -4F, 2F, 2F, 2F, 3F, false),
                PartPose.ZERO);
        head.addOrReplaceChild("horn3", CubeListBuilder.create()
                .texOffs(52, 15)
                .addBox(5.5F, -4F, 5F, 1F, 1F, 1F, false),
                PartPose.ZERO);
        head.addOrReplaceChild("horn4", CubeListBuilder.create()
                .texOffs(52, 4)
                .addBox(-8.5F, -4F, 0F, 2F, 2F, 4F, true),
                PartPose.ZERO);
        head.addOrReplaceChild("horn5", CubeListBuilder.create()
                .texOffs(52, 10)
                .addBox(-7.5F, -4F, 2F, 2F, 2F, 3F, true),
                PartPose.ZERO);
        head.addOrReplaceChild("horn6", CubeListBuilder.create()
                .texOffs(52, 15)
                .addBox(-6.5F, -4F, 5F, 1F, 1F, 1F, true),
                PartPose.ZERO);

        var body = root.addOrReplaceChild("body", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-6F, -10F, -9F, 12F, 10F, 13F, true),
                PartPose.offsetAndRotation(0F, 7F, 2F, 85F * Mth.DEG_TO_RAD, 0F, 0F));
        body.addOrReplaceChild("back", CubeListBuilder.create()
                .texOffs(0, 23)
                .addBox(-5.5F, 0F, -8.5F, 11F, 10F, 12F, true),
                PartPose.ZERO);
        body.addOrReplaceChild("udder", CubeListBuilder.create()
                .texOffs(28, 46)
                .addBox(-3.5F, 4F, -9.5F, 7F, 5F, 1F, true),
                PartPose.ZERO);

        var tail = root.addOrReplaceChild("tail", CubeListBuilder.create()
                .texOffs(28, 53)
                .addBox(-1F, 0F, -9.5F, 2F, 1F, 8F, true),
                PartPose.offsetAndRotation(0F, 7F, 11.5F, 90F * Mth.DEG_TO_RAD, 0F, 0F));
        tail.addOrReplaceChild("tailhairs", CubeListBuilder.create()
                .texOffs(35, 62)
                .addBox(-1F, 0F, -10.5F, 2F, 1F, 1F, true),
                PartPose.ZERO);

        var leg1 = root.addOrReplaceChild("leg1", CubeListBuilder.create()
                .texOffs(46, 18)
                .addBox(-1F, 0F, -3F, 4F, 11F, 5F, false),
                PartPose.offsetAndRotation(4F, 10F, -5F, 8F * Mth.DEG_TO_RAD, 0F, 0F));
        leg1.addOrReplaceChild("hoof1", CubeListBuilder.create()
                .texOffs(46, 34)
                .addBox(-0.5F, 8F, 0F, 3F, 6F, 3F, false),
                PartPose.rotation(-8F * Mth.DEG_TO_RAD, 0F, 0F));

        var leg2 = root.addOrReplaceChild("leg2", CubeListBuilder.create()
                .texOffs(46, 18)
                .addBox(-3F, 0F, -3F, 4F, 11F, 5F, true),
                PartPose.offsetAndRotation(-4F, 10F, -5F, 8F * Mth.DEG_TO_RAD, 0F, 0F));
        leg2.addOrReplaceChild("hoof2", CubeListBuilder.create()
                .texOffs(46, 34)
                .addBox(-2.5F, 8F, 0F, 3F, 6F, 3F, true),
                PartPose.rotation(-8F * Mth.DEG_TO_RAD, 0F, 0F));

        var leg3 = root.addOrReplaceChild("leg3", CubeListBuilder.create()
                .texOffs(48, 43)
                .addBox(-1F, 0F, -3F, 4F, 8F, 4F, false),
                PartPose.offset(4F, 10F, 10F));
        leg3.addOrReplaceChild("hoof3", CubeListBuilder.create()
                .texOffs(48, 55)
                .addBox(-0.5F, 8F, -2F, 3F, 6F, 3F, false),
                PartPose.ZERO);

        var leg4 = root.addOrReplaceChild("leg4", CubeListBuilder.create()
                .texOffs(48, 43)
                .addBox(-3F, 0F, -3F, 4F, 8F, 4F, true),
                PartPose.offset(-4F, 10F, 10F));
        leg4.addOrReplaceChild("hoof4", CubeListBuilder.create()
                .texOffs(48, 55)
                .addBox(-2.5F, 8F, -2F, 3F, 6F, 3F, true),
                PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return List.of(head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return List.of(body, tail, leg1, leg2, leg3, leg4);
    }

    @Override
    public void setupAnim(T buffalo, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        final float legspeed = 0.85F;

        head.xRot = headPitch * Mth.DEG_TO_RAD + Mth.HALF_PI;
        head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        leg1.xRot = Mth.cos(limbSwing * legspeed) * 1.4F * limbSwingAmount + 8F*Mth.DEG_TO_RAD;
        leg2.xRot = Mth.cos(limbSwing * legspeed + Mth.PI) * 1.4F * limbSwingAmount + 8F*Mth.DEG_TO_RAD;
        leg3.xRot = Mth.cos(limbSwing * legspeed + Mth.PI) * 1.4F * limbSwingAmount;
        leg4.xRot = Mth.cos(limbSwing * legspeed) * 1.4F * limbSwingAmount;
    }
}
