package totemic_commons.pokefenn.client.rendering.old;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTablet extends ModelBase
{
    //fields
    ModelRenderer leg1;
    ModelRenderer leg2;
    ModelRenderer leg3;
    ModelRenderer leg4;
    ModelRenderer base;
    ModelRenderer top;

    public ModelTablet()
    {
        textureWidth = 64;
        textureHeight = 32;

        leg1 = new ModelRenderer(this, 0, 0);
        leg1.addBox(0F, 0F, 0F, 1, 2, 1);
        leg1.setRotationPoint(-3F, 22F, 2F);
        leg1.setTextureSize(64, 32);
        leg1.mirror = true;
        setRotation(leg1, 0F, 0F, 0F);
        leg2 = new ModelRenderer(this, 0, 0);
        leg2.addBox(0F, 0F, 0F, 1, 2, 1);
        leg2.setRotationPoint(2F, 22F, 2F);
        leg2.setTextureSize(64, 32);
        leg2.mirror = true;
        setRotation(leg2, 0F, 0F, 0F);
        leg3 = new ModelRenderer(this, 0, 0);
        leg3.addBox(0F, 0F, 0F, 1, 2, 1);
        leg3.setRotationPoint(-3F, 22F, -2F);
        leg3.setTextureSize(64, 32);
        leg3.mirror = true;
        setRotation(leg3, 0F, 0F, 0F);
        leg4 = new ModelRenderer(this, 0, 0);
        leg4.addBox(0F, 0F, 0F, 1, 2, 1);
        leg4.setRotationPoint(2F, 22F, -2F);
        leg4.setTextureSize(64, 32);
        leg4.mirror = true;
        setRotation(leg4, 0F, 0F, 0F);
        base = new ModelRenderer(this, 0, 0);
        base.addBox(0F, 0F, 0F, 6, 1, 3);
        base.setRotationPoint(-3F, 21F, -1F);
        base.setTextureSize(64, 32);
        base.mirror = true;
        setRotation(base, 0F, 0F, 0F);
        top = new ModelRenderer(this, 0, 0);
        top.addBox(0F, 0F, 0F, 2, 4, 1);
        top.setRotationPoint(-1F, 17F, 0F);
        top.setTextureSize(64, 32);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        leg1.render(f5);
        leg2.render(f5);
        leg3.render(f5);
        leg4.render(f5);
        base.render(f5);
        top.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
