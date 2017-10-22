package pokefenn.totemic.client.rendering.entity;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.client.rendering.model.ModelBaykok;
import pokefenn.totemic.entity.boss.EntityBaykok;

public class BaykokRendering extends RenderBiped<EntityBaykok>
{
    private static final ResourceLocation baykokTexture = new ResourceLocation(Totemic.MOD_ID, "textures/entity/baykok.png");

    public BaykokRendering(RenderManager renderMgr)
    {
        super(renderMgr, new ModelBaykok(), 0.5F);
        addLayer(new LayerBipedArmor(this)
        {
            @Override
            protected void initArmor()
            {
                modelLeggings = new ModelBaykok(0.5F, true);
                modelArmor = new ModelBaykok(1.0F, true);
            }
        });
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBaykok entity)
    {
        return baykokTexture;
    }
}
