package totemic_commons.pokefenn.client.rendering.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTotemTorch extends ModelBase
{
    //fields
    ModelRenderer torchBase;
    ModelRenderer lightBase;
    ModelRenderer pole1;
    ModelRenderer pole2;
    ModelRenderer pole3;
    ModelRenderer pole4;
    ModelRenderer lightTop;
    ModelRenderer lightTopTop;

    public ModelTotemTorch()
    {
        textureWidth = 64;
        textureHeight = 32;

        torchBase = new ModelRenderer(this, 0, 0);
        torchBase.addBox(0F, 0F, 0F, 2, 16, 2);
        torchBase.setRotationPoint(-1F, 8F, -1F);
        torchBase.setTextureSize(64, 32);
        torchBase.mirror = true;
        setRotation(torchBase, 0F, 0F, 0F);
        lightBase = new ModelRenderer(this, 0, 0);
        lightBase.addBox(0F, 0F, 0F, 6, 1, 6);
        lightBase.setRotationPoint(-3F, 9F, -3F);
        lightBase.setTextureSize(64, 32);
        lightBase.mirror = true;
        setRotation(lightBase, 0F, 0F, 0F);
        pole1 = new ModelRenderer(this, 0, 0);
        pole1.addBox(0F, 0F, 0F, 1, 5, 1);
        pole1.setRotationPoint(2F, 4F, -3F);
        pole1.setTextureSize(64, 32);
        pole1.mirror = true;
        setRotation(pole1, 0F, 0F, 0F);
        pole2 = new ModelRenderer(this, 0, 0);
        pole2.addBox(0F, 0F, 0F, 1, 5, 1);
        pole2.setRotationPoint(2F, 4F, 2F);
        pole2.setTextureSize(64, 32);
        pole2.mirror = true;
        setRotation(pole2, 0F, 0F, 0F);
        pole3 = new ModelRenderer(this, 0, 0);
        pole3.addBox(0F, 0F, 0F, 1, 5, 1);
        pole3.setRotationPoint(-3F, 4F, -3F);
        pole3.setTextureSize(64, 32);
        pole3.mirror = true;
        setRotation(pole3, 0F, 0F, 0F);
        pole4 = new ModelRenderer(this, 0, 0);
        pole4.addBox(0F, 0F, 0F, 1, 5, 1);
        pole4.setRotationPoint(-3F, 4F, 2F);
        pole4.setTextureSize(64, 32);
        pole4.mirror = true;
        setRotation(pole4, 0F, 0F, 0F);
        lightTop = new ModelRenderer(this, 0, 0);
        lightTop.addBox(0F, 0F, 0F, 6, 1, 6);
        lightTop.setRotationPoint(-3F, 3F, -3F);
        lightTop.setTextureSize(64, 32);
        lightTop.mirror = true;
        setRotation(lightTop, 0F, 0F, 0F);
        lightTopTop = new ModelRenderer(this, 0, 0);
        lightTopTop.addBox(0F, 0F, 0F, 2, 1, 2);
        lightTopTop.setRotationPoint(-1F, 2F, -1F);
        lightTopTop.setTextureSize(64, 32);
        lightTopTop.mirror = true;
        setRotation(lightTopTop, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        torchBase.render(f5);
        lightBase.render(f5);
        pole1.render(f5);
        pole2.render(f5);
        pole3.render(f5);
        pole4.render(f5);
        lightTop.render(f5);
        lightTopTop.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}
