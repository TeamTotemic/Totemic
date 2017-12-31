package pokefenn.totemic.client.rendering.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelParrot - Sunconure11
 * Created using Tabula 7.0.0
 */
public class ModelBaldEagle extends ModelBase
{
    public ModelRenderer torso;
    public ModelRenderer rightWing;
    public ModelRenderer tailFeathers;
    public ModelRenderer head;
    public ModelRenderer leftLeg;
    public ModelRenderer leftWing;
    public ModelRenderer rightLeg;
    public ModelRenderer headTop;
    public ModelRenderer mouth;
    public ModelRenderer beak;
    public ModelRenderer beakTip;

    public ModelBaldEagle()
    {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.leftWing = new ModelRenderer(this, 36, 0);
        this.leftWing.setRotationPoint(-1.5F, 16.94F, -2.76F);
        this.leftWing.addBox(-0.5F, 0.0F, -1.5F, 1, 6, 3, 0.0F);
        this.setRotateAngle(leftWing, -0.6981317007977318F, -3.141592653589793F, 0.08726646259971647F);
        this.headTop = new ModelRenderer(this, 44, 0);
        this.headTop.setRotationPoint(0.0F, -2.0F, -1.0F);
        this.headTop.addBox(-1.0F, -0.5F, -2.0F, 2, 1, 4, 0.0F);
        this.tailFeathers = new ModelRenderer(this, 20, 0);
        this.tailFeathers.setRotationPoint(0.0F, 21.07F, 1.16F);
        this.tailFeathers.addBox(-1.5F, -1.0F, -1.0F, 3, 5, 1, 0.0F);
        this.setRotateAngle(tailFeathers, 1.083151333787681F, 0.0F, 0.0F);
        this.beak = new ModelRenderer(this, 53, 3);
        this.beak.setRotationPoint(-0.5F, -1.65F, -1.65F);
        this.beak.addBox(-0.5F, 0.1F, -3.3F, 2, 2, 3, 0.0F);
        this.setRotateAngle(beak, 0.0F, 0.00645771823237902F, 0.0F);
        this.beakTip = new ModelRenderer(this, 27, 5);
        this.beakTip.setRotationPoint(0.0F, 0.15F, 0.05F);
        this.beakTip.addBox(-0.5F, 1.2F, -3.35F, 2, 1, 1, 0.0F);
        this.torso = new ModelRenderer(this, 0, 0);
        this.torso.setRotationPoint(0.0F, 16.0F, -3.0F);
        this.torso.addBox(-1.5F, 0.0F, -1.5F, 3, 7, 3, 0.0F);
        this.setRotateAngle(torso, 0.49375364538919575F, 0.0F, 0.0F);
        this.rightLeg = new ModelRenderer(this, 41, 0);
        this.rightLeg.setRotationPoint(1.0F, 22.0F, -1.05F);
        this.rightLeg.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.mouth = new ModelRenderer(this, 52, 0);
        this.mouth.setRotationPoint(-0.5F, -0.5F, -1.5F);
        this.mouth.addBox(-0.5F, -1.0F, -0.5F, 2, 2, 1, 0.0F);
        this.head = new ModelRenderer(this, 28, 0);
        this.head.setRotationPoint(0.0F, 14.99F, -2.76F);
        this.head.addBox(-1.0F, -1.5F, -1.0F, 2, 3, 2, 0.0F);
        this.rightWing = new ModelRenderer(this, 12, 0);
        this.rightWing.setRotationPoint(1.5F, 16.94F, -2.76F);
        this.rightWing.addBox(-0.5F, 0.0F, -1.5F, 1, 6, 3, 0.0F);
        this.setRotateAngle(rightWing, -0.6981317007977318F, -3.141592653589793F, -0.08726646259971647F);
        this.leftLeg = new ModelRenderer(this, 9, 0);
        this.leftLeg.setRotationPoint(-1.0F, 22.0F, -1.05F);
        this.leftLeg.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.head.addChild(this.headTop);
        this.head.addChild(this.beak);
        this.beak.addChild(this.beakTip);
        this.head.addChild(this.mouth);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        this.leftWing.render(f5);
        this.tailFeathers.render(f5);
        this.torso.render(f5);
        this.rightLeg.render(f5);
        this.head.render(f5);
        this.rightWing.render(f5);
        this.leftLeg.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
