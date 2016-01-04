package totemic_commons.pokefenn.client.rendering.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BuffaloRendering extends RenderLiving<EntityBuffalo>
{
    public BuffaloRendering(ModelBase model, float shadowSize)
    {
        super(model, shadowSize);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityBuffalo var1)
    {
        return new ResourceLocation("totemic:textures/models/buffalo.png");
    }
}
