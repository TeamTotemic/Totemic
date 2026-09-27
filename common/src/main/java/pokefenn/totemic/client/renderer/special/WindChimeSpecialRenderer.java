package pokefenn.totemic.client.renderer.special;

import java.util.function.Consumer;

import org.joml.Vector3fc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.util.Unit;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.client.model.blockentity.WindChimeModel;
import pokefenn.totemic.client.renderer.blockentity.WindChimeRenderer;

public class WindChimeSpecialRenderer implements NoDataSpecialModelRenderer {
    private final WindChimeModel model;
    private final SpriteGetter sprites;

    public WindChimeSpecialRenderer(SpriteGetter sprites, WindChimeModel model) {
        this.sprites = sprites;
        this.model = model;
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        PoseStack poseStack = new PoseStack();
        this.model.root().getExtentsForGui(poseStack, output);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        submitNodeCollector.submitModel(model, Unit.INSTANCE, poseStack, lightCoords, overlayCoords, -1, WindChimeRenderer.TEXTURE, this.sprites, outlineColor, null);
    }

    public record Unbaked() implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<WindChimeSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new WindChimeSpecialRenderer.Unbaked());

        @Override
        public MapCodec<WindChimeSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public WindChimeSpecialRenderer bake(BakingContext context) {
            return new WindChimeSpecialRenderer(context.sprites(), new WindChimeModel(context.entityModelSet().bakeLayer(ModModelLayers.WIND_CHIME)));
        }
    }
}
