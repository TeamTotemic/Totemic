package pokefenn.totemic.client.renderer.entity;

import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.entity.Baykok;

public class BaykokRenderer extends MobRenderer<Baykok, SkeletonModel<Baykok>> {
    private static final ResourceLocation BAYKOK_TEXTURE = new ResourceLocation(TotemicAPI.MOD_ID, "textures/entity/baykok.png");

    public BaykokRenderer(Context ctx) {
        super(ctx, new SkeletonModel<Baykok>(ctx.bakeLayer(ModelLayers.SKELETON)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Baykok pEntity) {
        return BAYKOK_TEXTURE;
    }
}
