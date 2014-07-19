package totemic_commons.pokefenn.client.rendering.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ModelWindChime extends ModelBase
{

    /**
     * Thanks to CyanideX for the model! :D
     */

    public ModelRenderer connector1;
    public ModelRenderer base;
    public ModelRenderer hook;
    public ModelRenderer chime1;
    public ModelRenderer connector2;
    public ModelRenderer connector3;
    public ModelRenderer connector4;
    public ModelRenderer chime2;
    public ModelRenderer chime3;
    public ModelRenderer chime4;

    public ModelWindChime()
    {
        textureWidth = 64;
        textureHeight = 32;

        connector1 = new ModelRenderer(this, 0, 0);
        connector1.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
        connector1.setRotationPoint(0F, 11F, 2.5F);
        connector1.setTextureSize(64, 32);
        connector1.mirror = true;
        setRotation(connector1, 0F, 0F, 0F);
        base = new ModelRenderer(this, 0, 0);
        base.addBox(0F, 0F, 0F, 7, 1, 7);
        base.setRotationPoint(-3.5F, 10F, -3.5F);
        base.setTextureSize(64, 32);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        hook = new ModelRenderer(this, 0, 0);
        hook.addBox(0F, 0F, 0F, 1, 2, 1);
        hook.setRotationPoint(-0.5F, 8F, -0.5F);
        hook.setTextureSize(64, 32);
        hook.mirror = true;
        setRotation(hook, 0F, 0F, 0F);
        chime1 = new ModelRenderer(this, 0, 8);
        chime1.addBox(-1F, 2F, -1F, 2, 8, 2);
        chime1.setRotationPoint(0F, 11F, -2.5F);
        chime1.setTextureSize(64, 32);
        chime1.mirror = true;
        setRotation(chime1, 0F, 0F, 0F);
        connector2 = new ModelRenderer(this, 0, 0);
        connector2.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
        connector2.setRotationPoint(-2.5F, 11F, 0F);
        connector2.setTextureSize(64, 32);
        connector2.mirror = true;
        setRotation(connector2, 0F, 0F, 0F);
        connector3 = new ModelRenderer(this, 0, 0);
        connector3.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
        connector3.setRotationPoint(0F, 11F, -2.5F);
        connector3.setTextureSize(64, 32);
        connector3.mirror = true;
        setRotation(connector3, 0F, 0F, 0F);
        connector4 = new ModelRenderer(this, 0, 0);
        connector4.addBox(-0.5F, 0F, -0.5F, 1, 2, 1);
        connector4.setRotationPoint(2.5F, 11F, 0F);
        connector4.setTextureSize(64, 32);
        connector4.mirror = true;
        setRotation(connector4, 0F, 0F, 0F);
        chime2 = new ModelRenderer(this, 0, 8);
        chime2.addBox(-1F, 2F, -1F, 2, 5, 2);
        chime2.setRotationPoint(-2.5F, 11F, 0F);
        chime2.setTextureSize(64, 32);
        chime2.mirror = true;
        setRotation(chime2, 0F, 0F, 0F);
        chime3 = new ModelRenderer(this, 0, 8);
        chime3.addBox(-1F, 2F, -1F, 2, 7, 2);
        chime3.setRotationPoint(0F, 11F, 2.5F);
        chime3.setTextureSize(64, 32);
        chime3.mirror = true;
        setRotation(chime3, 0F, 0F, 0F);
        chime4 = new ModelRenderer(this, 0, 8);
        chime4.addBox(-1F, 2F, -1F, 2, 11, 2);
        chime4.setRotationPoint(2.5F, 11F, 0F);
        chime4.setTextureSize(64, 32);
        chime4.mirror = true;
        setRotation(chime4, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        connector1.render(f5);
        base.render(f5);
        hook.render(f5);
        chime1.render(f5);
        connector2.render(f5);
        connector3.render(f5);
        connector4.render(f5);
        chime2.render(f5);
        chime3.render(f5);
        chime4.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, (Entity) null);
    }
}
