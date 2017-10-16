package pokefenn.totemic.client.rendering.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.client.rendering.model.ModelBuffalo;
import pokefenn.totemic.entity.animal.EntityBuffalo;

public class BuffaloRendering extends RenderLiving<EntityBuffalo>
{
    private static final ResourceLocation buffaloTexture = new ResourceLocation(Totemic.MOD_ID, "textures/entity/buffalo.png");

    public BuffaloRendering(RenderManager renderMgr)
    {
        super(renderMgr, new ModelBuffalo(), 0.75F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBuffalo var1)
    {
        return buffaloTexture;
    }
}
