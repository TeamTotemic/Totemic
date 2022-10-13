package pokefenn.totemic.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;

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
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.tile.WindChimeBlockEntity;

public class WindChimeRenderer implements BlockEntityRenderer<WindChimeBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(TotemicAPI.MOD_ID, "textures/entity/wind_chime.png");

    private final ModelPart root;

    public WindChimeRenderer(BlockEntityRendererProvider.Context pContext) {
        this.root = pContext.bakeLayer(ModModelLayers.WIND_CHIME);
    }

    public static LayerDefinition createLayer() {
        var meshDef = new MeshDefinition();
        var parts = meshDef.getRoot();
        parts.addOrReplaceChild("base",
                CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(0F, 0F, 0F, 7, 1, 7)
                .mirror(),
                PartPose.offset(3.5F, 10F, 3.5F));
        return LayerDefinition.create(meshDef, 32, 32);
    }

    @Override
    public void render(WindChimeBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        var buffer = pBufferSource.getBuffer(RenderType.entitySolid(TEXTURE));
        root.render(pPoseStack, buffer, pPackedLight, pPackedOverlay);
    }
}
