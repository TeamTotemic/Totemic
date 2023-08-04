package pokefenn.totemic.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.client.ModModelLayers;
import pokefenn.totemic.client.model.BuffaloModel;
import pokefenn.totemic.entity.Buffalo;

public class BuffaloRenderer extends MobRenderer<Buffalo, BuffaloModel<Buffalo>> {
    private static final ResourceLocation BUFFALO_TEXTURE = Totemic.resloc("textures/entity/buffalo.png");

    public BuffaloRenderer(Context ctx) {
        super(ctx, new BuffaloModel<Buffalo>(ctx.bakeLayer(ModModelLayers.BUFFALO)), 0.75F);
    }

    @Override
    public ResourceLocation getTextureLocation(Buffalo buffalo) {
        return BUFFALO_TEXTURE;
    }
}
