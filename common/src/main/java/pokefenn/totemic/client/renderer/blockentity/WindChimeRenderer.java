package pokefenn.totemic.client.renderer.blockentity;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.util.Unit;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.block.music.entity.WindChimeBlockEntity;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.client.model.blockentity.WindChimeModel;

public class WindChimeRenderer implements BlockEntityRenderer<WindChimeBlockEntity, BlockEntityRenderState> {
    public static final SpriteId TEXTURE = Sheets.BLOCK_ENTITIES_MAPPER.apply(Totemic.resloc("wind_chime"));
    private final SpriteGetter sprites;
    private final WindChimeModel model;

    public WindChimeRenderer(BlockEntityRendererProvider.Context context) {
        this.sprites = context.sprites();
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
        submitNodeCollector.submitModel(model, Unit.INSTANCE, poseStack, state.lightCoords, OverlayTexture.NO_OVERLAY, -1, TEXTURE, this.sprites, 0, state.breakProgress);
        poseStack.popPose();
    }
}
