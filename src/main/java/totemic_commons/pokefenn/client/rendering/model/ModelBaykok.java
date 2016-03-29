package totemic_commons.pokefenn.client.rendering.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

/**
 * ModelBaykok - TechneToTabulaImporter
 * Created using Tabula 5.1.0
 */
public class ModelBaykok extends ModelBase
{
    public ModelRenderer leftfoot;
    public ModelRenderer leftleg;
    public ModelRenderer rightleg;
    public ModelRenderer rightfoot;
    public ModelRenderer pelvis;
    public ModelRenderer torso1;
    public ModelRenderer neck;
    public ModelRenderer righthand;
    public ModelRenderer torso2;
    public ModelRenderer clavicle;
    public ModelRenderer shoulder2;
    public ModelRenderer rightarm;
    public ModelRenderer leftarm;
    public ModelRenderer shoulder1;
    public ModelRenderer lefthand;
    public ModelRenderer head;

    public ModelBaykok()
    {
        this.textureWidth = 256;
        this.textureHeight = 256;
        this.rightleg = new ModelRenderer(this, 99, 125);
        this.rightleg.setRotationPoint(-1.0F, 9.0F, 1.0F);
        this.rightleg.addBox(-2.5F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
        this.setRotateAngle(rightleg, -0.27925267815589905F, -0.0F, 0.06981316953897475F);
        this.clavicle = new ModelRenderer(this, 45, 47);
        this.clavicle.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.clavicle.addBox(-5.0F, -9.0F, -1.0F, 10, 2, 4, 0.0F);
        this.setRotateAngle(clavicle, 0.08726646259971647F, -0.0F, 0.0F);
        this.righthand = new ModelRenderer(this, 18, 69);
        this.righthand.setRotationPoint(-6.0F, -6.0F, 0.0F);
        this.righthand.addBox(-2.0F, 6.0F, 0.9F, 3, 8, 4, 0.0F);
        this.setRotateAngle(righthand, -0.28623399732707F, -0.0F, 0.0F);
        this.torso2 = new ModelRenderer(this, 47, 56);
        this.torso2.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.torso2.addBox(-4.0F, -7.0F, -1.0F, 8, 6, 4, 0.0F);
        this.setRotateAngle(torso2, 0.08726646259971647F, -0.0F, 0.0F);
        this.rightarm = new ModelRenderer(this, 18, 53);
        this.rightarm.setRotationPoint(-6.0F, -6.0F, 0.0F);
        this.rightarm.addBox(-2.0F, 0.0F, -1.6F, 3, 8, 4, 0.0F);
        this.setRotateAngle(rightarm, 0.08726646259971647F, -0.0F, 0.0F);
        this.leftarm = new ModelRenderer(this, 18, 53);
        this.leftarm.mirror = true;
        this.leftarm.setRotationPoint(7.0F, -6.0F, 0.0F);
        this.leftarm.addBox(-2.0F, 0.0F, -1.6F, 3, 8, 4, 0.0F);
        this.setRotateAngle(leftarm, 0.08726646259971647F, -0.0F, 0.0F);
        this.rightfoot = new ModelRenderer(this, 99, 125);
        this.rightfoot.setRotationPoint(-1.0F, 9.0F, 1.0F);
        this.rightfoot.addBox(-3.0F, 7.0F, -3.5F, 3, 8, 3, 0.0F);
        this.leftfoot = new ModelRenderer(this, 99, 125);
        this.leftfoot.mirror = true;
        this.leftfoot.setRotationPoint(1.0F, 9.0F, 1.0F);
        this.leftfoot.addBox(0.0F, 7.0F, -3.5F, 3, 8, 3, 0.0F);
        this.pelvis = new ModelRenderer(this, 47, 82);
        this.pelvis.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.pelvis.addBox(-3.5F, -1.0F, -0.5F, 7, 5, 4, 0.0F);
        this.setRotateAngle(pelvis, -0.13962633907794952F, -0.0F, 0.0F);
        this.torso1 = new ModelRenderer(this, 47, 68);
        this.torso1.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.torso1.addBox(-4.0F, -2.0F, -1.0F, 8, 6, 4, 0.0F);
        this.setRotateAngle(torso1, 0.08726646259971647F, -0.0F, 0.0F);
        this.shoulder2 = new ModelRenderer(this, 18, 43);
        this.shoulder2.setRotationPoint(-5.0F, -6.0F, 0.0F);
        this.shoulder2.addBox(-3.0F, -2.0F, -1.6F, 4, 2, 4, 0.0F);
        this.setRotateAngle(shoulder2, 0.08726646259971647F, -0.0F, 0.0F);
        this.shoulder1 = new ModelRenderer(this, 18, 43);
        this.shoulder1.mirror = true;
        this.shoulder1.setRotationPoint(7.0F, -6.0F, 0.0F);
        this.shoulder1.addBox(-3.0F, -2.0F, -1.6F, 4, 2, 4, 0.0F);
        this.setRotateAngle(shoulder1, 0.08726646259971647F, -0.0F, 0.0F);
        this.leftleg = new ModelRenderer(this, 99, 110);
        this.leftleg.mirror = true;
        this.leftleg.setRotationPoint(1.0F, 9.0F, 1.0F);
        this.leftleg.addBox(-0.5F, 0.0F, -1.5F, 3, 8, 3, 0.0F);
        this.setRotateAngle(leftleg, -0.27925267815589905F, -0.0F, -0.06981316953897475F);
        this.neck = new ModelRenderer(this, 48, 37);
        this.neck.setRotationPoint(0.0F, 1.0F, -0.5F);
        this.neck.addBox(-3.0F, -10.0F, -0.5F, 6, 1, 4, 0.0F);
        this.setRotateAngle(neck, 0.08726646259971647F, -0.0F, 0.0F);
        this.head = new ModelRenderer(this, 45, 20);
        this.head.setRotationPoint(0.0F, -9.0F, -0.5F);
        this.head.addBox(-3.0F, -6.0F, -1.5F, 6, 6, 6, 0.0F);
        this.setRotateAngle(head, 0.08726646259971647F, -0.0F, 0.0F);
        this.lefthand = new ModelRenderer(this, 18, 69);
        this.lefthand.mirror = true;
        this.lefthand.setRotationPoint(7.0F, -6.0F, 0.0F);
        this.lefthand.addBox(-2.0F, 6.0F, 0.9F, 3, 8, 4, 0.0F);
        this.setRotateAngle(lefthand, -0.28623399732707F, -0.0F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.rightleg.render(f5);
        this.clavicle.render(f5);
        this.righthand.render(f5);
        this.torso2.render(f5);
        this.rightarm.render(f5);
        this.leftarm.render(f5);
        this.rightfoot.render(f5);
        this.leftfoot.render(f5);
        this.pelvis.render(f5);
        this.torso1.render(f5);
        this.shoulder2.render(f5);
        this.shoulder1.render(f5);
        this.leftleg.render(f5);
        this.neck.render(f5);
        this.head.render(f5);
        this.lefthand.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    private void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

        this.head.rotateAngleX = f4 / (180F / (float)Math.PI) + 0.08726646259971647F;
        this.head.rotateAngleY = f3 / (180F / (float)Math.PI);

        float leftarmX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        this.shoulder1.rotateAngleX = leftarmX + 0.08726646259971647F;
        this.leftarm.rotateAngleX = leftarmX + 0.08726646259971647F;
        this.lefthand.rotateAngleX = leftarmX - 0.28623399732707F;

        this.shoulder1.rotateAngleZ = this.leftarm.rotateAngleZ = this.lefthand.rotateAngleZ = 0.0F;

        float rightarmX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F;
        this.shoulder2.rotateAngleX = rightarmX + 0.08726646259971647F;
        this.rightarm.rotateAngleX = rightarmX + 0.08726646259971647F;
        this.righthand.rotateAngleX = rightarmX - 0.28623399732707F;

        this.shoulder2.rotateAngleZ = this.rightarm.rotateAngleZ = this.righthand.rotateAngleZ  = 0.0F;

        float leftlegX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.leftleg.rotateAngleX = leftlegX - 0.27925267815589905F;
        this.leftfoot.rotateAngleX = leftlegX;

        this.leftleg.rotateAngleY = this.leftfoot.rotateAngleY
                = 0.0F;

        float rightlegX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
        this.rightleg.rotateAngleX = rightlegX - 0.27925267815589905F;
        this.rightfoot.rotateAngleX = rightlegX;

        this.rightleg.rotateAngleY = this.rightfoot.rotateAngleY = 0.0F;
    }
}
