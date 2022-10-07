package pokefenn.totemic.client.rendering.entity;

import net.minecraft.client.model.CowModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.entity.Buffalo;

public class BuffaloRenderer extends MobRenderer<Buffalo, /*BuffaloModel*/CowModel<Buffalo>> {
    private static final ResourceLocation BUFFALO_TEXTURE = new ResourceLocation(TotemicAPI.MOD_ID, "textures/entity/buffalo.png");

    public BuffaloRenderer(Context ctx) {
        super(ctx, new CowModel<Buffalo>(ctx.bakeLayer(ModelLayers.COW)), 0.75F);
    }

    @Override
    public ResourceLocation getTextureLocation(Buffalo buffalo) {
        return BUFFALO_TEXTURE;
    }
}
