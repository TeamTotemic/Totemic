package totemic_commons.pokefenn.client.rendering.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.Totemic;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BuffaloRendering extends RenderLiving
{
    private static final ResourceLocation buffaloTexture = new ResourceLocation(Totemic.MOD_ID, "textures/models/buffalo.png");

    public BuffaloRendering(ModelBase model, float shadowSize)
    {
        super(model, shadowSize);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity var1)
    {
        return buffaloTexture;
    }
}
