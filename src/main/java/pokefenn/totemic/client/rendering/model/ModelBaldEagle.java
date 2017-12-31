package pokefenn.totemic.client.rendering.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import pokefenn.totemic.entity.animal.EntityBaldEagle;

/**
 * ModelBaldEagle - Sunconure11
 * Created using Tabula 7.0.0
 */
public class ModelBaldEagle extends ModelBase
{
    private ModelRenderer torso;
    private ModelRenderer rightWing;
    private ModelRenderer tailFeathers;
    private ModelRenderer head;
    private ModelRenderer leftLeg;
    private ModelRenderer leftWing;
    private ModelRenderer rightLeg;
    private ModelRenderer headTop;
    private ModelRenderer mouth;
    private ModelRenderer beak;
    private ModelRenderer beakTip;

    private State state = State.STANDING;

    public ModelBaldEagle()
    {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.headTop = new ModelRenderer(this, 8, 0);
        this.headTop.setRotationPoint(0.0F, -2.0F, -1.0F);
        this.headTop.addBox(-1.0F, -0.5F, -2.0F, 2, 1, 4, 0.0F);
        this.head = new ModelRenderer(this, 10, 5);
        this.head.setRotationPoint(0.0F, 15.0F, -2.76F);
        this.head.addBox(-1.0F, -1.5F, -1.0F, 2, 3, 2, 0.0F);
        this.leftLeg = new ModelRenderer(this, 8, 20);
        this.leftLeg.setRotationPoint(-1.0F, 22.0F, -1.05F);
        this.leftLeg.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.beakTip = new ModelRenderer(this, 2, 8);
        this.beakTip.setRotationPoint(0.0F, 0.15F, 0.05F);
        this.beakTip.addBox(-0.5F, 1.2F, -3.35F, 2, 1, 1, 0.0F);
        this.rightWing = new ModelRenderer(this, 20, 10);
        this.rightWing.setRotationPoint(1.5F, 16.94F, -2.76F);
        this.rightWing.addBox(-0.5F, 0.0F, -1.5F, 1, 6, 3, 0.0F);
        this.setRotation(rightWing, -0.6981317007977318F, -3.141592653589793F, -0.08726646259971647F);
        this.mouth = new ModelRenderer(this, 2, 0);
        this.mouth.setRotationPoint(-0.5F, -0.5F, -1.5F);
        this.mouth.addBox(-0.5F, -1.0F, -0.5F, 2, 2, 1, 0.0F);
        this.tailFeathers = new ModelRenderer(this, 0, 20);
        this.tailFeathers.setRotationPoint(0.0F, 21.07F, 1.16F);
        this.tailFeathers.addBox(-1.5F, -1.0F, -1.0F, 3, 5, 1, 0.0F);
        this.setRotation(tailFeathers, 1.083151333787681F, 0.0F, 0.0F);
        this.beak = new ModelRenderer(this, 0, 3);
        this.beak.setRotationPoint(-0.5F, -1.65F, -1.65F);
        this.beak.addBox(-0.5F, 0.1F, -3.3F, 2, 2, 3, 0.0F);
        this.setRotation(beak, 0.0F, 0.00645771823237902F, 0.0F);
        this.torso = new ModelRenderer(this, 8, 10);
        this.torso.setRotationPoint(0.0F, 16.0F, -3.0F);
        this.torso.addBox(-1.5F, 0.0F, -1.5F, 3, 7, 3, 0.0F);
        this.setRotation(torso, 0.49375364538919575F, 0.0F, 0.0F);
        this.leftWing = new ModelRenderer(this, 0, 10);
        this.leftWing.setRotationPoint(-1.5F, 16.94F, -2.76F);
        this.leftWing.addBox(-0.5F, 0.0F, -1.5F, 1, 6, 3, 0.0F);
        this.setRotation(leftWing, -0.6981317007977318F, -3.141592653589793F, 0.08726646259971647F);
        this.rightLeg = new ModelRenderer(this, 12, 20);
        this.rightLeg.setRotationPoint(1.0F, 22.0F, -1.05F);
        this.rightLeg.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1, 0.0F);
        this.head.addChild(this.headTop);
        this.beak.addChild(this.beakTip);
        this.head.addChild(this.mouth);
        this.head.addChild(this.beak);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, -0.75F, 0);
        GlStateManager.scale(1.5F, 1.5F, 1.5F);

        if(isChild)
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 8F * scale, 0.5F * scale);
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            this.head.render(scale);
            GlStateManager.popMatrix();

            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
        }
        else
        {
            this.head.render(scale);
        }

        this.torso.render(scale);
        this.rightWing.render(scale);
        this.leftWing.render(scale);
        this.tailFeathers.render(scale);
        this.leftLeg.render(scale);
        this.rightLeg.render(scale);

        GlStateManager.popMatrix();
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        float f = ageInTicks * 0.3F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleZ = 0.0F;
        this.head.rotationPointX = 0.0F;
        this.torso.rotationPointX = 0.0F;
        this.tailFeathers.rotationPointX = 0.0F;
        this.leftWing.rotationPointX = -1.5F;
        this.rightWing.rotationPointX = 1.5F;

        if(this.state == State.SITTING)
        {
            return;
        }
        else if(this.state == State.STANDING)
        {
            this.leftLeg.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.rightLeg.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        }

        this.head.rotationPointY = 15.0F + f;
        this.tailFeathers.rotateAngleX = 1.015F + MathHelper.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
        this.tailFeathers.rotationPointY = 21.07F + f;
        this.torso.rotationPointY = 16.0F + f;
        this.rightWing.rotateAngleZ = -0.0873F - ageInTicks;
        this.rightWing.rotationPointY = 16.94F + f;
        this.leftWing.rotateAngleZ = 0.0873F + ageInTicks;
        this.leftWing.rotationPointY = 16.94F + f;
        this.leftLeg.rotationPointY = 22.0F + f;
        this.rightLeg.rotationPointY = 22.0F + f;
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        this.torso.rotateAngleX = 0.4937F;
        this.rightWing.rotateAngleX = -((float)Math.PI * 2F / 9F);
        this.rightWing.rotateAngleY = -(float)Math.PI;
        this.leftWing.rotateAngleX = -((float)Math.PI * 2F / 9F);
        this.leftWing.rotateAngleY = -(float)Math.PI;
        this.leftLeg.rotateAngleX = -0.0299F;
        this.rightLeg.rotateAngleX = -0.0299F;
        this.leftLeg.rotationPointY = 22.0F;
        this.rightLeg.rotationPointY = 22.0F;

        if(entity instanceof EntityBaldEagle)
        {
            EntityBaldEagle eagle = (EntityBaldEagle) entity;
            if(eagle.isSitting())
            {
                this.head.rotationPointY = 16.9F;
                this.tailFeathers.rotateAngleX = 1.5388988F;
                this.tailFeathers.rotationPointY = 22.97F;
                this.torso.rotationPointY = 17.9F;
                this.rightWing.rotateAngleZ = -0.0873F;
                this.rightWing.rotationPointY = 18.84F;
                this.leftWing.rotateAngleZ = 0.0873F;
                this.leftWing.rotationPointY = 18.84F;
                this.leftLeg.rotationPointY += 1F;
                this.rightLeg.rotationPointY += 1F;
                this.leftLeg.rotateAngleX += 1F;
                this.rightLeg.rotateAngleX += 1F;
                this.state = State.SITTING;
            }
            else if(eagle.isFlying())
            {
                this.leftLeg.rotateAngleX += ((float)Math.PI * 2F / 9F);
                this.rightLeg.rotateAngleX += ((float)Math.PI * 2F / 9F);
                this.state = State.FLYING;
            }
            else
            {
                this.state = State.STANDING;
            }

            this.leftLeg.rotateAngleZ = 0.0F;
            this.rightLeg.rotateAngleZ = 0.0F;
        }
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    private void setRotation(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    private static enum State
    {
        FLYING, STANDING, SITTING;
    }
}
