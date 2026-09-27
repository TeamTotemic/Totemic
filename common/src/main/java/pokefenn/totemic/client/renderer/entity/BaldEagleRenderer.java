package pokefenn.totemic.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.client.model.BaldEagleModel;
import pokefenn.totemic.client.model.BaldEagleModel.BaldEagleRenderState;
import pokefenn.totemic.entity.BaldEagle;

public class BaldEagleRenderer extends MobRenderer<BaldEagle, BaldEagleRenderState, BaldEagleModel> {
    private static final Identifier BALD_EAGLE_TEXTURE = Totemic.resloc("textures/entity/bald_eagle.png");
    private final AdultAndBabyModelPair<BaldEagleModel> models;

    public BaldEagleRenderer(Context ctx) {
        var adultModel = new BaldEagleModel(ctx.bakeLayer(ModModelLayers.BALD_EAGLE));
        var babyModel = new BaldEagleModel(ctx.bakeLayer(ModModelLayers.BALD_EAGLE_BABY));
        super(ctx, adultModel, 0.4F);
        this.models = new AdultAndBabyModelPair<>(adultModel, babyModel);
    }

    @Override
    public Identifier getTextureLocation(BaldEagleRenderState state) {
        return BALD_EAGLE_TEXTURE;
    }

    @Override
    public BaldEagleRenderState createRenderState() {
        return new BaldEagleRenderState();
    }

    @Override
    public void extractRenderState(BaldEagle entity, BaldEagleRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        float flap = Mth.lerp(partialTicks, entity.oFlap, entity.flap);
        float flapSpeed = Mth.lerp(partialTicks, entity.oFlapSpeed, entity.flapSpeed);
        state.flapAngle = (Mth.sin(flap) + 1.0F) * flapSpeed;
        state.pose = BaldEagleModel.getPose(entity);
    }

    @Override
    public void submit(BaldEagleRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        this.model = this.models.getModel(state.isBaby);
        poseStack.pushPose();
        poseStack.translate(0F, -0.75F, 0F);
        poseStack.scale(1.5F, 1.5F, 1.5F);
        super.submit(state, poseStack, submitNodeCollector, camera);
        poseStack.popPose();
    }
}
