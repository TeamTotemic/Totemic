package pokefenn.totemic.client.rendering.entity;

import javax.annotation.Nullable;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.client.rendering.model.ModelBaldEagle;
import pokefenn.totemic.entity.animal.EntityBaldEagle;

public class BaldEagleRendering extends RenderLiving<EntityBaldEagle>
{
    private static final ResourceLocation BALD_EAGLE_TEXTURE = new ResourceLocation(Totemic.MOD_ID, "textures/entity/bald_eagle.png");

    public BaldEagleRendering(RenderManager rendermanager)
    {
        super(rendermanager, new ModelBaldEagle(), 0.4F);
    }

    @Override
    @Nullable
    protected ResourceLocation getEntityTexture(EntityBaldEagle entity)
    {
        return BALD_EAGLE_TEXTURE;
    }

    @Override
    public float handleRotationFloat(EntityBaldEagle entity, float partialTicks)
    {
        return this.getCustomBob(entity, partialTicks);
    }

    private float getCustomBob(EntityBaldEagle eagle, float partialTicks)
    {
        float flap = eagle.oFlap + (eagle.flap - eagle.oFlap) * partialTicks;
        float flapSpeed = eagle.oFlapSpeed + (eagle.flapSpeed - eagle.oFlapSpeed) * partialTicks;
        return (MathHelper.sin(flap) + 1.0F) * flapSpeed;
    }
}
