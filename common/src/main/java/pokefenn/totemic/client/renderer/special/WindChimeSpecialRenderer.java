package pokefenn.totemic.client.renderer.special;

import java.util.function.Consumer;

import org.joml.Quaternionf;
import org.joml.Vector3fc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.util.Unit;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.client.model.blockentity.WindChimeModel;
import pokefenn.totemic.client.renderer.blockentity.WindChimeRenderer;

public class WindChimeSpecialRenderer implements NoDataSpecialModelRenderer {
    private final WindChimeModel model;

    public WindChimeSpecialRenderer(WindChimeModel model) {
        this.model = model;
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        PoseStack poseStack = new PoseStack();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(new Quaternionf(0, 0, 1, 0)); //180° rotation around Z-axis
        this.model.root().getExtentsForGui(poseStack, output);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        poseStack.pushPose();
        poseStack.translate(0.5F, 1.5F, 0.5F);
        poseStack.mulPose(new Quaternionf(0, 0, 1, 0)); //180° rotation around Z-axis
        submitNodeCollector.submitModel(model, Unit.INSTANCE, poseStack, WindChimeRenderer.TEXTURE, lightCoords, overlayCoords, outlineColor, null);
        poseStack.popPose();
    }

    public record Unbaked() implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<WindChimeSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new WindChimeSpecialRenderer.Unbaked());

        @Override
        public MapCodec<WindChimeSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public WindChimeSpecialRenderer bake(BakingContext context) {
            return new WindChimeSpecialRenderer(new WindChimeModel(context.entityModelSet().bakeLayer(ModModelLayers.WIND_CHIME)));
        }
    }
}
