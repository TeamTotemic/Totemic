package pokefenn.totemic.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.block.music.entity.WindChimeBlockEntity;
import pokefenn.totemic.client.ModModelLayers;

public class WindChimeRenderer implements BlockEntityRenderer<WindChimeBlockEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TotemicAPI.MOD_ID, "textures/entity/wind_chime.png");

    private final ModelPart root;

    public WindChimeRenderer(BlockEntityRendererProvider.Context pContext) {
        this.root = pContext.bakeLayer(ModModelLayers.WIND_CHIME);
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

    @Override
    public void render(WindChimeBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5, 1.5, 0.5);
        pPoseStack.mulPose(new Quaternion(0, 0, 1, 0)); //180° rotation around Z-axis
        var buffer = pBufferSource.getBuffer(RenderType.entitySolid(TEXTURE));
        root.render(pPoseStack, buffer, pPackedLight, pPackedOverlay);
        pPoseStack.popPose();
    }
}
