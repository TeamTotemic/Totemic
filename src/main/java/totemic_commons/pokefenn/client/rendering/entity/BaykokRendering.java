package totemic_commons.pokefenn.client.rendering.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.client.rendering.model.ModelBaykok;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;

@SideOnly(Side.CLIENT)
public class BaykokRendering extends RenderBiped
{
    private static final ResourceLocation baykokTexture = new ResourceLocation(Totemic.MOD_ID, "textures/models/baykok.png");

    public BaykokRendering()
    {
        super(new ModelBaykok(), 0.5F);
    }

    @Override
    public void doRender(EntityLiving entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        BossStatus.setBossStatus((EntityBaykok) entity, true);
        super.doRender(entity, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return baykokTexture;
    }
}
