package pokefenn.totemic.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.client.model.BuffaloModel;
import pokefenn.totemic.entity.Buffalo;

public class BuffaloRenderer extends MobRenderer<Buffalo, LivingEntityRenderState, BuffaloModel> {
    private static final Identifier BUFFALO_TEXTURE = Totemic.resloc("textures/entity/buffalo.png");
    private final AdultAndBabyModelPair<BuffaloModel> models;

    public BuffaloRenderer(Context ctx) {
        var adultModel = new BuffaloModel(ctx.bakeLayer(ModModelLayers.BUFFALO));
        var babyModel = new BuffaloModel(ctx.bakeLayer(ModModelLayers.BUFFALO_BABY));
        super(ctx, adultModel, 0.75F);
        this.models = new AdultAndBabyModelPair<>(adultModel, babyModel);
    }

    @Override
    public Identifier getTextureLocation(LivingEntityRenderState state) {
        return BUFFALO_TEXTURE;
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void submit(LivingEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        this.model = this.models.getModel(state.isBaby);
        poseStack.pushPose();
        poseStack.translate(0F, -0.75F, 0F);
        poseStack.scale(1.5F, 1.5F, 1.5F);
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }
}
