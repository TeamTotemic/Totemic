package totemic_commons.pokefenn.client.rendering.entity;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;

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
        //FIXME: Doesn't work like that client side. shootingEntity needs to somehow be synchronized.
        /*if(entity.shootingEntity == Minecraft.getMinecraft().thePlayer)
            super.doRender(entity, x, y, z, entityYaw, partialTicks);*/
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityArrow entity)
    {
        return arrowTextures;
    }
}
