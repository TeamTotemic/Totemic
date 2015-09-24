package totemic_commons.pokefenn.client.rendering.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ModelBuffalo extends ModelBase
{
    ModelRenderer udder;
    ModelRenderer back;
    ModelRenderer back2;
    ModelRenderer head;
    ModelRenderer body;
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;
    ModelRenderer leg4;
    ModelRenderer horn1;
    ModelRenderer horn2;

    public ModelBuffalo()
    {
        textureWidth = 64;
        textureHeight = 64;

        udder = new ModelRenderer(this, 0, 0);
        udder.addBox(-3F, 0F, 0F, 6, 1, 6);
        udder.setRotationPoint(0F, 12F, 0F);
        udder.setTextureSize(64, 32);
        udder.mirror = true;
        setRotation(udder, 0F, 0F, 0F);
        back = new ModelRenderer(this, 0, 0);
        back.addBox(-6F, -2F, -8F, 12, 2, 10);
        back.setRotationPoint(0F, 2F, 0F);
        back.setTextureSize(64, 32);
        back.mirror = true;
        setRotation(back, 0F, 0F, 0F);
        back2 = new ModelRenderer(this, 0, 0);
        back2.addBox(-6F, -1F, 1F, 12, 1, 3);
        back2.setRotationPoint(0F, 2F, 0F);
        back2.setTextureSize(64, 32);
        back2.mirror = true;
        setRotation(back2, -0.0349066F, 0F, 0F);
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-4F, -5F, -6F, 8, 8, 6);
        head.setRotationPoint(0F, 4F, -8F);
        head.setTextureSize(64, 32);
        head.mirror = true;
        setRotation(head, 0F, 0F, 0F);
        body = new ModelRenderer(this, 18, 4);
        body.addBox(-6F, -10F, -7F, 12, 18, 10);
        body.setRotationPoint(0F, 5F, 2F);
        body.setTextureSize(64, 32);
        body.mirror = true;
        setRotation(body, 1.570796F, 0F, 0F);
        leg1 = new ModelRenderer(this, 0, 16);
        leg1.addBox(-3F, 0F, -2F, 4, 12, 4);
        leg1.setRotationPoint(-3F, 12F, 7F);
        leg1.setTextureSize(64, 32);
        leg1.mirror = true;
        setRotation(leg1, 0F, 0F, 0F);
        leg2 = new ModelRenderer(this, 0, 16);
        leg2.addBox(-1F, 0F, -2F, 4, 12, 4);
        leg2.setRotationPoint(3F, 12F, 7F);
        leg2.setTextureSize(64, 32);
        leg2.mirror = true;
        setRotation(leg2, 0F, 0F, 0F);
        leg3 = new ModelRenderer(this, 0, 16);
        leg3.addBox(-3F, 0F, -3F, 4, 12, 4);
        leg3.setRotationPoint(-3F, 12F, -5F);
        leg3.setTextureSize(64, 32);
        leg3.mirror = true;
        setRotation(leg3, 0F, 0F, 0F);
        leg4 = new ModelRenderer(this, 0, 16);
        leg4.addBox(-1F, 0F, -3F, 4, 12, 4);
        leg4.setRotationPoint(3F, 12F, -5F);
        leg4.setTextureSize(64, 32);
        leg4.mirror = true;
        setRotation(leg4, 0F, 0F, 0F);
        horn1 = new ModelRenderer(this, 22, 0);
        horn1.addBox(-5F, -6F, -7F, 1, 3, 1);
        horn1.setRotationPoint(0F, 3F, -7F);
        horn1.setTextureSize(64, 32);
        horn1.mirror = true;
        setRotation(horn1, 0.2094395F, 0F, 0F);
        horn2 = new ModelRenderer(this, 22, 0);
        horn2.addBox(4F, -6F, -7F, 1, 3, 1);
        horn2.setRotationPoint(0F, 3F, -7F);
        horn2.setTextureSize(64, 32);
        horn2.mirror = true;
        setRotation(horn2, 0.2094395F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        udder.render(f5);
        back.render(f5);
        back2.render(f5);
        head.render(f5);
        body.render(f5);
        leg1.render(f5);
        leg2.render(f5);
        leg3.render(f5);
        leg4.render(f5);
        horn1.render(f5);
        horn2.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
    }

}

