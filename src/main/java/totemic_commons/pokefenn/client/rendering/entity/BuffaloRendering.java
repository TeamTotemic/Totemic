package totemic_commons.pokefenn.client.rendering.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.client.rendering.model.ModelBuffalo;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;

public class BuffaloRendering extends RenderLiving<EntityBuffalo>
{
    private static final ResourceLocation buffaloTexture = new ResourceLocation("totemic:textures/models/buffalo.png");

    public BuffaloRendering(RenderManager renderMgr)
    {
        super(renderMgr, new ModelBuffalo(), 0.75F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBuffalo entity)
    {
        return buffaloTexture;
    }
}
