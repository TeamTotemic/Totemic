package totemic_commons.pokefenn.client.rendering.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.client.rendering.model.ModelBaykok;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;

public class BaykokRendering extends RenderLiving<EntityBaykok>
{
    private static final ResourceLocation baykokTexture = new ResourceLocation("totemic:textures/models/baykok.png");

    public BaykokRendering(RenderManager renderMgr)
    {
        super(renderMgr, new ModelBaykok(), 0.5F);
        addLayer(new LayerHeldItem(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBaykok entity)
    {
        return baykokTexture;
    }
}
