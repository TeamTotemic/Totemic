package totemic_commons.pokefenn.client.rendering.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelHalberd extends ModelBase
{
    ModelRenderer handle;
    ModelRenderer ring;
    ModelRenderer ring2;
    ModelRenderer handleEnd;
    ModelRenderer top1;
    ModelRenderer top2;
    ModelRenderer extendingBlade11;
    ModelRenderer bladeStart1;
    ModelRenderer extendingBlade12;
    ModelRenderer extendingBlade13;
    ModelRenderer extendingBlade14;
    ModelRenderer extendingBlade15;
    ModelRenderer extendingBlade16;
    ModelRenderer supportingBlade12;
    ModelRenderer bladeDetail1;
    ModelRenderer extendingBlade21;
    ModelRenderer bladeDetail2;
    ModelRenderer bladeStart2;
    ModelRenderer extendingBlade22;
    ModelRenderer extendingBlade23;
    ModelRenderer extendingBlade24;
    ModelRenderer extendingBlade25;
    ModelRenderer extendingBlade26;
    ModelRenderer bladeDetail3;
    ModelRenderer extendingBlade27;

    public ModelHalberd()
    {
        textureWidth = 64;
        textureHeight = 32;

        handle = new ModelRenderer(this, 0, 0);
        handle.addBox(0F, 0F, 0F, 2, 28, 2);
        handle.setRotationPoint(-1F, -4F, -1F);
        handle.setTextureSize(64, 32);
        handle.mirror = true;
        setRotation(handle, 0F, 0F, 0F);
        ring = new ModelRenderer(this, 0, 0);
        ring.addBox(0F, 0F, 0F, 4, 1, 4);
        ring.setRotationPoint(-2F, 9F, -2F);
        ring.setTextureSize(64, 32);
        ring.mirror = true;
        setRotation(ring, 0F, 0F, 0F);
        ring2 = new ModelRenderer(this, 0, 0);
        ring2.addBox(0F, 0F, 0F, 4, 1, 4);
        ring2.setRotationPoint(-2F, 3F, -2F);
        ring2.setTextureSize(64, 32);
        ring2.mirror = true;
        setRotation(ring2, 0F, 0F, 0F);
        handleEnd = new ModelRenderer(this, 0, 0);
        handleEnd.addBox(0F, 0F, 0F, 6, 7, 6);
        handleEnd.setRotationPoint(-3F, -11F, -3F);
        handleEnd.setTextureSize(64, 32);
        handleEnd.mirror = true;
        setRotation(handleEnd, 0F, 0F, 0F);
        top1 = new ModelRenderer(this, 0, 0);
        top1.addBox(0F, 0F, 0F, 4, 1, 4);
        top1.setRotationPoint(-2F, -12F, -2F);
        top1.setTextureSize(64, 32);
        top1.mirror = true;
        setRotation(top1, 0F, 0F, 0F);
        top2 = new ModelRenderer(this, 0, 0);
        top2.addBox(0F, 0F, 0F, 2, 1, 2);
        top2.setRotationPoint(-1F, -13F, -1F);
        top2.setTextureSize(64, 32);
        top2.mirror = true;
        setRotation(top2, 0F, 0F, 0F);
        extendingBlade11 = new ModelRenderer(this, 0, 0);
        extendingBlade11.addBox(0F, 0F, 0F, 2, 2, 6);
        extendingBlade11.setRotationPoint(-1F, -8F, -9F);
        extendingBlade11.setTextureSize(64, 32);
        extendingBlade11.mirror = true;
        setRotation(extendingBlade11, 0F, 0F, 0F);
        bladeStart1 = new ModelRenderer(this, 0, 0);
        bladeStart1.addBox(0F, 0F, 0F, 2, 13, 2);
        bladeStart1.setRotationPoint(-1F, -12F, -12F);
        bladeStart1.setTextureSize(64, 32);
        bladeStart1.mirror = true;
        setRotation(bladeStart1, 0F, 0F, 0F);
        extendingBlade12 = new ModelRenderer(this, 0, 0);
        extendingBlade12.addBox(0F, 0F, 0F, 2, 11, 1);
        extendingBlade12.setRotationPoint(-1F, -11F, -13F);
        extendingBlade12.setTextureSize(64, 32);
        extendingBlade12.mirror = true;
        setRotation(extendingBlade12, 0F, 0F, 0F);
        extendingBlade13 = new ModelRenderer(this, 0, 0);
        extendingBlade13.addBox(0F, 0F, 0F, 2, 9, 1);
        extendingBlade13.setRotationPoint(-1F, -10F, -14F);
        extendingBlade13.setTextureSize(64, 32);
        extendingBlade13.mirror = true;
        setRotation(extendingBlade13, 0F, 0F, 0F);
        extendingBlade14 = new ModelRenderer(this, 0, 0);
        extendingBlade14.addBox(0F, 0F, 0F, 2, 7, 1);
        extendingBlade14.setRotationPoint(-1F, -9F, -15F);
        extendingBlade14.setTextureSize(64, 32);
        extendingBlade14.mirror = true;
        setRotation(extendingBlade14, 0F, 0F, 0F);
        extendingBlade15 = new ModelRenderer(this, 0, 0);
        extendingBlade15.addBox(0F, 0F, 0F, 2, 5, 1);
        extendingBlade15.setRotationPoint(-1F, -8F, -16F);
        extendingBlade15.setTextureSize(64, 32);
        extendingBlade15.mirror = true;
        setRotation(extendingBlade15, 0F, 0F, 0F);
        extendingBlade16 = new ModelRenderer(this, 0, 0);
        extendingBlade16.addBox(0F, 0F, 0F, 2, 3, 1);
        extendingBlade16.setRotationPoint(-1F, -8F, -17F);
        extendingBlade16.setTextureSize(64, 32);
        extendingBlade16.mirror = true;
        setRotation(extendingBlade16, 0F, 0F, 0F);
        supportingBlade12 = new ModelRenderer(this, 0, 0);
        supportingBlade12.addBox(0F, 0F, 0F, 2, 4, 1);
        supportingBlade12.setRotationPoint(-1F, -9F, -10F);
        supportingBlade12.setTextureSize(64, 32);
        supportingBlade12.mirror = true;
        setRotation(supportingBlade12, 0F, 0F, 0F);
        bladeDetail1 = new ModelRenderer(this, 0, 0);
        bladeDetail1.addBox(0F, -1F, -1F, 2, 2, 2);
        bladeDetail1.setRotationPoint(-1F, -5F, -6F);
        bladeDetail1.setTextureSize(64, 32);
        bladeDetail1.mirror = true;
        setRotation(bladeDetail1, 0F, 0F, 0F);
        extendingBlade21 = new ModelRenderer(this, 0, 0);
        extendingBlade21.addBox(0F, 0F, 0F, 2, 2, 6);
        extendingBlade21.setRotationPoint(-1F, -8F, 3F);
        extendingBlade21.setTextureSize(64, 32);
        extendingBlade21.mirror = true;
        setRotation(extendingBlade21, 0F, 0F, 0F);
        bladeDetail2 = new ModelRenderer(this, 0, 0);
        bladeDetail2.addBox(0F, 0F, 0F, 2, 2, 2);
        bladeDetail2.setRotationPoint(-1F, -6F, 5F);
        bladeDetail2.setTextureSize(64, 32);
        bladeDetail2.mirror = true;
        setRotation(bladeDetail2, 0F, 0F, 0F);
        bladeStart2 = new ModelRenderer(this, 0, 0);
        bladeStart2.addBox(0F, 0F, 0F, 2, 13, 2);
        bladeStart2.setRotationPoint(-1F, -12F, 9F);
        bladeStart2.setTextureSize(64, 32);
        bladeStart2.mirror = true;
        setRotation(bladeStart2, 0F, 0F, 0F);
        extendingBlade22 = new ModelRenderer(this, 0, 0);
        extendingBlade22.addBox(0F, 0F, 0F, 2, 11, 1);
        extendingBlade22.setRotationPoint(-1F, -11F, 11F);
        extendingBlade22.setTextureSize(64, 32);
        extendingBlade22.mirror = true;
        setRotation(extendingBlade22, 0F, 0F, 0F);
        extendingBlade23 = new ModelRenderer(this, 0, 0);
        extendingBlade23.addBox(0F, 0F, 0F, 2, 9, 1);
        extendingBlade23.setRotationPoint(-1F, -10F, 12F);
        extendingBlade23.setTextureSize(64, 32);
        extendingBlade23.mirror = true;
        setRotation(extendingBlade23, 0F, 0F, 0F);
        extendingBlade24 = new ModelRenderer(this, 0, 0);
        extendingBlade24.addBox(0F, 0F, 0F, 2, 7, 1);
        extendingBlade24.setRotationPoint(-1F, -9F, 13F);
        extendingBlade24.setTextureSize(64, 32);
        extendingBlade24.mirror = true;
        setRotation(extendingBlade24, 0F, 0F, 0F);
        extendingBlade25 = new ModelRenderer(this, 0, 0);
        extendingBlade25.addBox(0F, 0F, 0F, 2, 5, 1);
        extendingBlade25.setRotationPoint(-1F, -8F, 14F);
        extendingBlade25.setTextureSize(64, 32);
        extendingBlade25.mirror = true;
        setRotation(extendingBlade25, 0F, 0F, 0F);
        extendingBlade26 = new ModelRenderer(this, 0, 0);
        extendingBlade26.addBox(0F, 0F, 0F, 2, 3, 1);
        extendingBlade26.setRotationPoint(-1F, -7F, 15F);
        extendingBlade26.setTextureSize(64, 32);
        extendingBlade26.mirror = true;
        setRotation(extendingBlade26, 0F, 0F, 0F);
        bladeDetail3 = new ModelRenderer(this, 0, 0);
        bladeDetail3.addBox(0F, 0F, 0F, 2, 1, 8);
        bladeDetail3.setRotationPoint(-1F, -10F, 2F);
        bladeDetail3.setTextureSize(64, 32);
        bladeDetail3.mirror = true;
        setRotation(bladeDetail3, 0.0371786F, 0F, 0F);
        extendingBlade27 = new ModelRenderer(this, 0, 0);
        extendingBlade27.addBox(0F, 0F, 0F, 2, 3, 1);
        extendingBlade27.setRotationPoint(-1F, -7F, -17F);
        extendingBlade27.setTextureSize(64, 32);
        extendingBlade27.mirror = true;
        setRotation(extendingBlade27, 0F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        handle.render(f5);
        ring.render(f5);
        ring2.render(f5);
        handleEnd.render(f5);
        top1.render(f5);
        top2.render(f5);
        extendingBlade11.render(f5);
        bladeStart1.render(f5);
        extendingBlade12.render(f5);
        extendingBlade13.render(f5);
        extendingBlade14.render(f5);
        extendingBlade15.render(f5);
        extendingBlade16.render(f5);
        supportingBlade12.render(f5);
        bladeDetail1.render(f5);
        extendingBlade21.render(f5);
        bladeDetail2.render(f5);
        bladeStart2.render(f5);
        extendingBlade22.render(f5);
        extendingBlade23.render(f5);
        extendingBlade24.render(f5);
        extendingBlade25.render(f5);
        extendingBlade26.render(f5);
        bladeDetail3.render(f5);
        extendingBlade27.render(f5);
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
