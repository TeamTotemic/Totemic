package totemic_commons.pokefenn.client.rendering.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.client.rendering.model.ModelBaldEagle;
import totemic_commons.pokefenn.entity.animal.EntityBaldEagle;

public class BaldEagleRendering extends RenderLiving
{
    private static final ResourceLocation baldEagleTexture = new ResourceLocation(Totemic.MOD_ID, "textures/models/bald_eagle.png");

    public BaldEagleRendering()
    {
        super(new ModelBaldEagle(), 0.4F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return baldEagleTexture;
    }

    // used as wingAngle parameter for ModelBaldEagle.setRotationAngles
    @Override
    protected float handleRotationFloat(EntityLivingBase entity, float partialTicks)
    {
        EntityBaldEagle eagle = (EntityBaldEagle) entity;
        float flap = eagle.oFlap + (eagle.flap - eagle.oFlap) * partialTicks;
        float flapSpeed = eagle.oFlapSpeed + (eagle.flapSpeed - eagle.oFlapSpeed) * partialTicks;
        return (MathHelper.sin(flap) + 1.0F) * flapSpeed;
    }
}
