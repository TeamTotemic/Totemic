package pokefenn.totemic.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.client.model.BaykokModel;
import pokefenn.totemic.entity.Baykok;

public class BaykokRenderer extends HumanoidMobRenderer<Baykok, BaykokModel<Baykok>> {
    private static final ResourceLocation BAYKOK_TEXTURE = new ResourceLocation(TotemicAPI.MOD_ID, "textures/entity/baykok.png");

    public BaykokRenderer(Context ctx) {
        super(ctx, new BaykokModel<Baykok>(ctx.bakeLayer(ModModelLayers.BAYKOK)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Baykok pEntity) {
        return BAYKOK_TEXTURE;
    }
}
