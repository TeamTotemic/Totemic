package pokefenn.totemic.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.client.model.BaykokModel;
import pokefenn.totemic.entity.Baykok;

public class BaykokRenderer extends HumanoidMobRenderer<Baykok, HumanoidRenderState, BaykokModel> {
    private static final Identifier BAYKOK_TEXTURE = Totemic.resloc("textures/entity/baykok.png");

    public BaykokRenderer(EntityRendererProvider.Context context) {
        super(context, new BaykokModel(context.bakeLayer(ModModelLayers.BAYKOK)), 0.5F);
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState state) {
        return BAYKOK_TEXTURE;
    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }
}
