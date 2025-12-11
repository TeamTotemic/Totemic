package totemic_commons.pokefenn.client.rendering.entity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.Totemic;

public class InvisArrowRendering extends RenderArrow
{
    private static final ResourceLocation baykokArrowTexture = new ResourceLocation(Totemic.MOD_ID, "textures/models/baykok_arrow.png");

    @Override
    protected ResourceLocation getEntityTexture(EntityArrow p_110775_1_)
    {
        return baykokArrowTexture;
    }

    @Override
    public void doRender(EntityArrow entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        if(entity.shootingEntity == Minecraft.getMinecraft().thePlayer)
        {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            super.doRender(entity, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }
}
