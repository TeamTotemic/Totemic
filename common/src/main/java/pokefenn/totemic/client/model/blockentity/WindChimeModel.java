package pokefenn.totemic.client.model.blockentity;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;

public class WindChimeModel extends Model<Unit> {
    public WindChimeModel(ModelPart root) {
        super(root, RenderTypes::entitySolid);
    }

    public static LayerDefinition createLayer() {
        var mesh = new MeshDefinition();
        var root = mesh.getRoot();
        root.addOrReplaceChild("base", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(0F, 0F, 0F, 7F, 1F, 7F, true),
                PartPose.offset(-3.5F, 10F, -3.5F));
        root.addOrReplaceChild("chime1", CubeListBuilder.create()
                .texOffs(0, 8)
                .addBox(-1F, 2F, -1F, 2F, 8F, 2F, true),
                PartPose.offset(0F, 11F, -2.5F));
        root.addOrReplaceChild("chime2", CubeListBuilder.create()
                .texOffs(0, 8)
                .addBox(-1F, 2F, -1F, 2F, 5F, 2F, true),
                PartPose.offset(-2.5F, 11F, 0F));
        root.addOrReplaceChild("chime3", CubeListBuilder.create()
                .texOffs(0, 8)
                .addBox(-1F, 2F, -1F, 2F, 7F, 2F, true),
                PartPose.offset(0F, 11F, 2.5F));
        root.addOrReplaceChild("chime4", CubeListBuilder.create()
                .texOffs(0, 8)
                .addBox(-1F, 2F, -1F, 2F, 11F, 2F, true),
                PartPose.offset(2.5F, 11F, 0F));
        root.addOrReplaceChild("connector1", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-0.5F, 0F, -0.5F, 1F, 2F, 1F, true),
                PartPose.offset(0F, 11F, 2.5F));
        root.addOrReplaceChild("connector2", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-0.5F, 0F, -0.5F, 1F, 2F, 1F, true),
                PartPose.offset(-2.5F, 11F, 0F));
        root.addOrReplaceChild("connector3", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-0.5F, 0F, -0.5F, 1F, 2F, 1F, true),
                PartPose.offset(0F, 11F, -2.5F));
        root.addOrReplaceChild("connector4", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-0.5F, 0F, -0.5F, 1F, 2F, 1F, true),
                PartPose.offset(2.5F, 11F, 0F));
        root.addOrReplaceChild("hook", CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(0F, 0F, 0F, 1F, 2F, 1F, true),
                PartPose.offset(-0.5F, 8F, -0.5F));
        return LayerDefinition.create(mesh, 32, 32);
    }
}
