package totemic_commons.pokefenn.client.rendering.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.entity.projectile.EntityInvisArrow;
import totemic_commons.pokefenn.lib.Resources;

public class InvisArrowRendering extends RenderArrow<EntityInvisArrow>
{
    public InvisArrowRendering(RenderManager renderMgr)
    {
        super(renderMgr);
    }

    @Override
    public void doRender(EntityInvisArrow entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        if(entity.isShotByLocalPlayer())
        {
            GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            super.doRender(entity, x, y, z, entityYaw, partialTicks);
            GL11.glPopAttrib();
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityInvisArrow entity)
    {
        return Resources.TEXTURE_BAYKOK_ARROW;
    }
}
