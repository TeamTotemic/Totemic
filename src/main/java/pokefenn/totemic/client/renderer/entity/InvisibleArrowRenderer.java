package pokefenn.totemic.client.renderer.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.entity.InvisibleArrow;

public class InvisibleArrowRenderer extends ArrowRenderer<InvisibleArrow> {
    private static final ResourceLocation INVIS_ARROW_TEXTURE = new ResourceLocation(TotemicAPI.MOD_ID, "textures/entity/invis_arrow.png");

    public InvisibleArrowRenderer(Context pContext) {
        super(pContext);
    }

    @Override
    public ResourceLocation getTextureLocation(InvisibleArrow pEntity) {
        return INVIS_ARROW_TEXTURE;
    }
}
