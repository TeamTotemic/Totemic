package totemic_commons.pokefenn.entity.boss;

import java.util.Random;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityEfreet extends EntityMob implements IBossDisplayData, IRangedAttackMob
{
    private static final IEntitySelector attackEntitySelector = new IEntitySelector()
    {
        @Override
        public boolean isEntityApplicable(Entity entity)
        {
            return entity instanceof EntityPlayer;
        }
    };


    public EntityEfreet(World par1World)
    {
        super(par1World);
        this.isImmuneToFire = true;
        this.setHealth(this.getMaxHealth());
        this.experienceValue = 75;

        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 40, 20.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false, false, attackEntitySelector));
        this.targetTasks.addTask(3, new EntityAIWander(this, 1));

    }

    @Override
    public void onLivingUpdate()
    {
        super.onUpdate();

        Random rand = new Random();

        if(!this.worldObj.isRemote)
        {
            if(this.isBurning())
            {
                if(this.worldObj.getWorldTime() % 30L == 0)
                    if(rand.nextBoolean())
                        this.heal(rand.nextInt(3));
            }
        }

        if(this.worldObj.isRemote)
        {
            if(this.worldObj.getWorldTime() % 10L == 0)
                for(int i = 1; i < 10; i++)
                    worldObj.spawnParticle("flame", this.posX, this.posY, this.posZ, 0.1F, 0.1F, 0.1F);
        }
    }


    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase entity, float var2)
    {
        this.fireball2(0, entity);
    }

    private void spawnFireball(int par1, double par2, double par4, double par6, boolean par8)
    {
        this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1014, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
        double xPosWorkingOut = this.xPosWorkingOut(par1);
        double yPosWorkingOut = this.yPosWorkingOut(par1);
        double zPosworkingOut = this.zPosworkingOut(par1);
        double d6 = par2 - xPosWorkingOut;
        double d7 = par4 - yPosWorkingOut;
        double d8 = par6 - zPosworkingOut;
        EntityLargeFireball entityFireball = new EntityLargeFireball(this.worldObj, this, d6, d7, d8);

        if(par8)
        {
            //    entityFireball.
        }

        entityFireball.posY = yPosWorkingOut;
        entityFireball.posX = xPosWorkingOut;
        entityFireball.posZ = zPosworkingOut;
        this.worldObj.spawnEntityInWorld(entityFireball);
    }

    private void fireball2(int par1, EntityLivingBase par2EntityLivingBase)
    {
        this.spawnFireball(par1, par2EntityLivingBase.posX, par2EntityLivingBase.posY + par2EntityLivingBase.getEyeHeight() * 0.5D, par2EntityLivingBase.posZ, par1 == 0 && this.rand.nextFloat() < 0.001F);
    }

    private double yPosWorkingOut(int par1)
    {
        return par1 <= 0 ? this.posY + 3.0D : this.posY + 2.2D;
    }

    private double xPosWorkingOut(int par1)
    {
        if(par1 <= 0)
        {
            return this.posX;
        } else
        {
            float f = (this.renderYawOffset + 180 * (par1 - 1)) / 180.0F * (float) Math.PI;
            float f1 = MathHelper.cos(f);
            return this.posX + f1 * 1.3D;
        }
    }

    private double zPosworkingOut(int par1)
    {
        if(par1 <= 0)
        {
            return this.posZ;
        } else
        {
            float f = (this.renderYawOffset + 180 * (par1 - 1)) / 180.0F * (float) Math.PI;
            float f1 = MathHelper.sin(f);
            return this.posZ + f1 * 1.3D;
        }
    }

}
