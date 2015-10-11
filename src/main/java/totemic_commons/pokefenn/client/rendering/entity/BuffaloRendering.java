package totemic_commons.pokefenn.client.rendering.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BuffaloRendering extends RenderLiving
{
    public BuffaloRendering(ModelBase model, float shadowSize)
    {
        super(model, shadowSize);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity var1)
    {
        return new ResourceLocation("totemic:textures/models/buffalo.png");
    }
}
