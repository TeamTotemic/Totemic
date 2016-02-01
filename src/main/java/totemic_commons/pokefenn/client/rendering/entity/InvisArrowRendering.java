package totemic_commons.pokefenn.client.rendering.entity;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.entity.projectile.EntityInvisArrow;

public class InvisArrowRendering extends RenderArrow
{
    private static final ResourceLocation arrowTextures = new ResourceLocation("textures/entity/arrow.png"); //TODO

    public InvisArrowRendering(RenderManager renderMgr)
    {
        super(renderMgr);
    }

    @Override
    public void doRender(EntityArrow entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if(((EntityInvisArrow)entity).isShotByPlayer())
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityArrow entity)
    {
        return arrowTextures;
    }
}
