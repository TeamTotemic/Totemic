package totemic_commons.pokefenn.client.rendering.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.client.rendering.model.ModelBaykok;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;

public class BaykokRendering extends RenderLiving<EntityBaykok>
{
    public BaykokRendering(RenderManager renderMgr)
    {
        super(renderMgr, new ModelBaykok(), 0.5f);
        addLayer(new LayerHeldItem(this));
    }

    @Override
    public void doRender(EntityBaykok entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        BossStatus.setBossStatus(entity, false);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBaykok entity)
    {
        return new ResourceLocation("totemic:textures/models/baykok.png");
    }
}
