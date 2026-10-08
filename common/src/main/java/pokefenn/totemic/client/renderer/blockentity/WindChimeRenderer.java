package pokefenn.totemic.client.renderer.blockentity;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Unit;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.music.entity.WindChimeBlockEntity;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.client.model.blockentity.WindChimeModel;

public class WindChimeRenderer implements BlockEntityRenderer<WindChimeBlockEntity, BlockEntityRenderState> {
    public static final Identifier TEXTURE = Totemic.resloc("textures/entity/wind_chime.png");
    private final WindChimeModel model;

    public WindChimeRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new WindChimeModel(context.bakeLayer(ModModelLayers.WIND_CHIME));
    }

    @Override
    public BlockEntityRenderState createRenderState() {
        return new BlockEntityRenderState();
    }

    @Override
    public void submit(BlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(new Quaternionf(0, 0, 1, 0)); //180° rotation around Z-axis
        //TODO: Swing animation
        submitNodeCollector.submitModel(model, Unit.INSTANCE, poseStack, TEXTURE, state.lightCoords, OverlayTexture.NO_OVERLAY, 0, state.breakProgress);
        poseStack.popPose();
    }
}
