package totemic_commons.pokefenn.client.rendering.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

/**
 * ModelBaykok - TechneToTabulaImporter
 * Created using Tabula 5.1.0
 */
public class ModelBaykok extends ModelBiped //This must be ModelBiped, else LayerHeldItem does not work
{
    private ModelRenderer leftfoot;
    private ModelRenderer leftleg;
    private ModelRenderer rightleg;
    private ModelRenderer rightfoot;
    private ModelRenderer pelvis;
    private ModelRenderer torso1;
    private ModelRenderer neck;
    private ModelRenderer righthand;
    private ModelRenderer torso2;
    private ModelRenderer clavicle;
    private ModelRenderer shoulder2;
    private ModelRenderer rightarm;
    private ModelRenderer leftarm;
    private ModelRenderer shoulder1;
    private ModelRenderer lefthand;
    private ModelRenderer head;
    private ModelRenderer leftfootbone;
    private ModelRenderer rightfootbone;
    private ModelRenderer leftlegbone;
    private ModelRenderer rightlegbone;
    private ModelRenderer claviclebone;
    private ModelRenderer leftarmbone;
    private ModelRenderer rightarmbone;
    private ModelRenderer lefthandbone;
    private ModelRenderer righthandbone;
    private ModelRenderer skull;
    private ModelRenderer spine;
    private ModelRenderer pelvisbone;
    private ModelRenderer ribs;

    public ModelBaykok()
    {
        this.aimedBow = true;

        this.textureWidth = 256;
        this.textureHeight = 256;
        this.rightleg = new ModelRenderer(this, 99, 110);
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
        this.torso2.addBox(-4.0F, -7.0F, -1.0F, 8, 5, 4, 0.0F);
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
        this.shoulder2.addBox(-3.0F, -2.0F, -1.6F, 3, 2, 4, 0.0F);
        this.setRotateAngle(shoulder2, 0.08726646259971647F, -0.0F, 0.0F);
        this.shoulder1 = new ModelRenderer(this, 18, 43);
        this.shoulder1.mirror = true;
        this.shoulder1.setRotationPoint(8.0F, -6.0F, 0.0F);
        this.shoulder1.addBox(-3.0F, -2.0F, -1.6F, 3, 2, 4, 0.0F);
        this.setRotateAngle(shoulder1, 0.08726646259971647F, -0.0F, 0.0F);
        this.leftleg = new ModelRenderer(this, 99, 110);
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
        this.leftfootbone = new ModelRenderer(this, 120, 125);
        this.leftfootbone.setRotationPoint(1.0F, 9.0F, 1.0F);
        this.leftfootbone.addBox(0.5F, 7.0F, -3.0F, 2, 8, 2, 0.0F);
        this.rightfootbone = new ModelRenderer(this, 120, 125);
        this.rightfootbone.setRotationPoint(-1.0F, 9.0F, 1.0F);
        this.rightfootbone.addBox(-2.5F, 7.0F, -3.0F, 2, 8, 2, 0.0F);
        this.leftlegbone = new ModelRenderer(this, 120, 110);
        this.leftlegbone.setRotationPoint(1.0F, 9.0F, 1.0F);
        this.leftlegbone.addBox(0.0F, -1.0F, -1.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(leftlegbone, -0.2792526803190927F, 0.0F, -0.06981317007977318F);
        this.rightlegbone = new ModelRenderer(this, 120, 110);
        this.rightlegbone.setRotationPoint(-1.0F, 9.0F, 1.0F);
        this.rightlegbone.addBox(-2.0F, -1.0F, -1.0F, 2, 9, 2, 0.0F);
        this.setRotateAngle(rightlegbone, -0.2792526803190927F, 0.0F, 0.06981317007977318F);
        this.claviclebone = new ModelRenderer(this, 145, 47);
        this.claviclebone.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.claviclebone.addBox(-7.5F, -8.5F, 0.0F, 15, 1, 2, 0.0F);
        this.setRotateAngle(claviclebone, 0.08726646259971647F, -0.0F, 0.0F);
        this.leftarmbone = new ModelRenderer(this, 120, 53);
        this.leftarmbone.mirror = true;
        this.leftarmbone.setRotationPoint(7.0F, -6.0F, 0.0F);
        this.leftarmbone.addBox(-1.5F, -0.5F, -0.6F, 2, 8, 2, 0.0F);
        this.setRotateAngle(leftarmbone, 0.08726646259971647F, -0.0F, 0.0F);
        this.rightarmbone = new ModelRenderer(this, 120, 53);
        this.rightarmbone.setRotationPoint(-6.0F, -6.0F, 0.0F);
        this.rightarmbone.addBox(-1.5F, -0.5F, -0.6F, 2, 8, 2, 0.0F);
        this.setRotateAngle(rightarmbone, 0.08726646259971647F, -0.0F, 0.0F);
        this.lefthandbone = new ModelRenderer(this, 120, 69);
        this.lefthandbone.mirror = true;
        this.lefthandbone.setRotationPoint(7.0F, -6.0F, 0.0F);
        this.lefthandbone.addBox(-1.5F, 6.5F, 1.9F, 2, 7, 2, 0.0F);
        this.setRotateAngle(lefthandbone, -0.28623399732707F, -0.0F, 0.0F);
        this.righthandbone = new ModelRenderer(this, 120, 69);
        this.righthandbone.setRotationPoint(-6.0F, -6.0F, 0.0F);
        this.righthandbone.addBox(-1.5F, 6.5F, 1.9F, 2, 7, 2, 0.0F);
        this.setRotateAngle(righthandbone, -0.28623399732707F, -0.0F, 0.0F);
        this.skull = new ModelRenderer(this, 120, 20);
        this.skull.setRotationPoint(0.0F, -9.0F, -0.5F);
        this.skull.addBox(-2.5F, -5.5F, -1.0F, 5, 5, 5, 0.0F);
        this.setRotateAngle(skull, 0.08726646259971647F, -0.0F, 0.0F);
        this.spine = new ModelRenderer(this, 95, 37);
        this.spine.setRotationPoint(0.0F, 1.0F, -0.5F);
        this.spine.addBox(-1.0F, -10.0F, 1.0F, 2, 16, 2, 0.0F);
        this.setRotateAngle(spine, 0.08726646259971647F, -0.0F, 0.0F);
        this.pelvisbone = new ModelRenderer(this, 120, 82);
        this.pelvisbone.setRotationPoint(0.0F, 5.0F, 0.0F);
        this.pelvisbone.addBox(-3.0F, 1.3F, 0.0F, 6, 2, 3, 0.0F);
        this.setRotateAngle(pelvisbone, -0.13962634015954636F, -0.0F, 0.0F);
        this.ribs = new ModelRenderer(this, 145, 56);
        this.ribs.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.ribs.addBox(-3.5F, -7.0F, -0.6F, 7, 11, 3, 0.0F);
        this.setRotateAngle(ribs, 0.08726646259971647F, -0.0F, 0.0F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_ENABLE_BIT);
        //The bones have to be rendered first due to the translucenct skin
        this.ribs.render(f5);
        GL11.glEnable(GL11.GL_CULL_FACE);
        this.leftfootbone.render(f5);
        this.rightfootbone.render(f5);
        this.leftlegbone.render(f5);
        this.rightlegbone.render(f5);
        this.claviclebone.render(f5);
        this.leftarmbone.render(f5);
        this.rightarmbone.render(f5);
        this.lefthandbone.render(f5);
        this.righthandbone.render(f5);
        this.skull.render(f5);
        this.spine.render(f5);
        this.pelvisbone.render(f5);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.leftfoot.render(f5);
        this.rightfoot.render(f5);
        this.leftleg.render(f5);
        this.rightleg.render(f5);
        this.pelvis.render(f5);
        this.torso1.render(f5);
        this.torso2.render(f5);
        this.clavicle.render(f5);
        this.shoulder1.render(f5);
        this.shoulder2.render(f5);
        this.lefthand.render(f5);
        this.righthand.render(f5);
        this.leftarm.render(f5);
        this.rightarm.render(f5);
        this.head.render(f5);
        this.neck.render(f5);
        GL11.glPopAttrib();
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

        this.head.rotateAngleX = this.skull.rotateAngleX = f4 / (180F / (float)Math.PI) + 0.08726646259971647F;
        this.head.rotateAngleY = this.skull.rotateAngleY = f3 / (180F / (float)Math.PI);

        float leftarmX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;
        this.shoulder1.rotateAngleX = leftarmX + 0.08726646259971647F;
        this.leftarm.rotateAngleX = this.leftarmbone.rotateAngleX = leftarmX + 0.08726646259971647F;
        this.lefthand.rotateAngleX = this.lefthandbone.rotateAngleX = leftarmX - 0.28623399732707F;

        this.shoulder1.rotateAngleZ = this.leftarm.rotateAngleZ = this.leftarmbone.rotateAngleZ
                = this.lefthand.rotateAngleZ = this.lefthandbone.rotateAngleZ = 0.0F;

        float rightarmX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 2.0F * f1 * 0.5F - (float)Math.PI * 0.2F;
        this.shoulder2.rotateAngleX = rightarmX + 0.08726646259971647F;
        this.rightarm.rotateAngleX = this.rightarmbone.rotateAngleX = rightarmX + 0.08726646259971647F;
        this.righthand.rotateAngleX = this.righthandbone.rotateAngleX = rightarmX - 0.28623399732707F;

        this.shoulder2.rotateAngleZ = this.rightarm.rotateAngleZ = this.rightarmbone.rotateAngleZ
                = this.righthand.rotateAngleZ = this.righthandbone.rotateAngleZ = 0.0F;

        float leftlegX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.leftleg.rotateAngleX = this.leftlegbone.rotateAngleX = leftlegX - 0.27925267815589905F;
        this.leftfoot.rotateAngleX = this.leftfootbone.rotateAngleX = leftlegX;

        this.leftleg.rotateAngleY = this.leftlegbone.rotateAngleY
                = this.leftfoot.rotateAngleY = this.leftfootbone.rotateAngleY = 0.0F;

        float rightlegX = MathHelper.cos(f * 0.6662F + (float)Math.PI) * 1.4F * f1;
        this.rightleg.rotateAngleX = this.rightlegbone.rotateAngleX = rightlegX - 0.27925267815589905F;
        this.rightfoot.rotateAngleX = this.rightfootbone.rotateAngleX = rightlegX;

        this.rightleg.rotateAngleY = this.rightlegbone.rotateAngleY
                = this.rightfoot.rotateAngleY = this.rightfootbone.rotateAngleY = 0.0F;
    }

    @Override
    public void postRenderArm(float scale)
    {
        this.righthand.postRender(scale);
        GlStateManager.translate(-0.075F, 0.25F, 0.09F);
    }
}
