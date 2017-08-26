package pokefenn.totemic.entity.animal;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import pokefenn.totemic.lib.Resources;

public class EntityBuffalo extends EntityCow
{
    //public boolean isSheared = false;

    public EntityBuffalo(World world)
    {
        super(world);
        setSize(1.35F, 1.95F);
    }

    @Override
    protected void initEntityAI()
    {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackMelee(this, 3.0D, false));
        tasks.addTask(2, new EntityAIPanic(this, 2.0D));
        tasks.addTask(3, new EntityAIMate(this, 1.0D));
        tasks.addTask(4, new EntityAITempt(this, 1.25D, Items.WHEAT, false));
        tasks.addTask(5, new EntityAIFollowParent(this, 1.25D));
        tasks.addTask(6, new EntityAIWander(this, 1.0D));
        tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        tasks.addTask(8, new EntityAILookIdle(this));
        targetTasks.addTask(1,
                new EntityAIHurtByTarget(this, false)
                {
                    @Override
                    protected boolean isSuitableTarget(@Nullable EntityLivingBase target, boolean includeInvincibles)
                    {
                        return target instanceof AbstractSkeleton && super.isSuitableTarget(target, includeInvincibles);
                    }
                });
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity)
    {
        boolean attacked = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        if(attacked)
        {
            if(entity instanceof EntityLivingBase)
                ((EntityLivingBase) entity).knockBack(this, 0.75F, Math.sin(Math.toRadians(rotationYaw)), -Math.cos(Math.toRadians(rotationYaw)));

            applyEnchantments(this, entity);
        }
        return attacked;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    @Override
    protected float getSoundPitch()
    {
        return super.getSoundPitch() - 0.2F;
    }

    /*@Override
    public void writeEntityToNBT(NBTTagCompound tag)
    {
        super.writeEntityToNBT(tag);
        tag.setBoolean("isSheared", isSheared);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag)
    {
        super.readEntityFromNBT(tag);
        isSheared = tag.getBoolean("isSheared");
    }*/

    @Override
    protected ResourceLocation getLootTable()
    {
        return Resources.LOOT_BUFFALO;
    }

    //TODO
    /*@Override
    public boolean interact(EntityPlayer player)
    {
        ItemStack itemstack = player.inventory.getCurrentItem();
        Random rand = new Random();

        if(itemstack != null)
        {
            if(!isSheared && itemstack.getItem() instanceof ItemShears)
            {
                itemstack.damageItem(1, player);
                EntityItem entityItem = new EntityItem(world, posX, posY, posZ, new ItemStack(ModItems.buffaloItems, 2 + rand.nextInt(3), ItemBuffaloDrops.hair));
                world.spawnEntityInWorld(entityItem);

                return true;
            }
        }

        return super.interact(player);

    }*/

    @Override
    public EntityBuffalo createChild(EntityAgeable var1)
    {
        return new EntityBuffalo(world);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
        return 2 + world.rand.nextInt(4);
    }
}
