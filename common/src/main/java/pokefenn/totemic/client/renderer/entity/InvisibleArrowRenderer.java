package pokefenn.totemic.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.entity.InvisibleArrow;

public class InvisibleArrowRenderer extends ArrowRenderer<InvisibleArrow, ArrowRenderState> {
    private static final Identifier INVIS_ARROW_TEXTURE = Totemic.resloc("textures/entity/invis_arrow.png");

    public InvisibleArrowRenderer(Context pContext) {
        super(pContext);
    }

    @Override
    public void submit(ArrowRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yRot - 90.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(state.xRot));
        var renderType = RenderTypes.entityTranslucent(this.getTextureLocation(state));
        submitNodeCollector.submitModel(
            this.model, state, poseStack, renderType, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null
        );
        poseStack.popPose();

        // copied from EntityRenderer (as super.super is not possible)
        this.submitNameDisplay(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    public boolean shouldRender(InvisibleArrow entity, Frustum culler, double camX, double camY, double camZ) {
        return entity.getOwner() == Minecraft.getInstance().player && super.shouldRender(entity, culler, camX, camY, camZ);
    }

    @Override
    public Identifier getTextureLocation(ArrowRenderState state) {
        return INVIS_ARROW_TEXTURE;
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }
}
