package pokefenn.totemic.client.rendering.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.entity.projectile.EntityInvisArrow;

public class InvisArrowRendering extends RenderArrow<EntityInvisArrow>
{
    private static final ResourceLocation baykokArrowTexture = new ResourceLocation(Totemic.MOD_ID, "textures/entity/baykok_arrow.png");

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
        return baykokArrowTexture;
    }
}
