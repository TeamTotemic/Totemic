package totemic_commons.pokefenn.entity.animal;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.item.ItemBuffaloDrops;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityBuffalo extends EntityCow
{
    public static boolean isSheared = false;

    public EntityBuffalo(World world)
    {
        super(world);
        setSize(0.9F, 1.3F);
        getNavigator().setAvoidsWater(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIPanic(this, 2.0D));
        tasks.addTask(2, new EntityAIMate(this, 1.0D));
        tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.wheat, false));
        tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        tasks.addTask(5, new EntityAIWander(this, 1.0D));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    public boolean isAIEnabled()
    {
        return true;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(35.0D);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.15000000298023224D);
    }

    @Override
    protected void updateAITasks()
    {
        super.updateAITasks();
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.cow.hurt";
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.cow.hurt";
    }

    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        playSound("mob.cow.step", 0.15F, 1.0F);
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

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("isSheared", isSheared);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        isSheared = par1NBTTagCompound.getBoolean("isSheared");
    }

    @Override
    protected void dropFewItems(boolean hitByPlayer, int looting)
    {
        int j = rand.nextInt(3) + rand.nextInt(1 + looting);
        for(int k = 0; k < j; ++k)
        {
            entityDropItem(new ItemStack(ModItems.buffaloItems, 1, ItemBuffaloDrops.Type.hide.ordinal()), 0F);
        }

        j = rand.nextInt(5) + 1 + rand.nextInt(2 + 2*looting);
        for(int k = 0; k < j; ++k)
        {
            if(isBurning())
            {
                dropItem(Items.cooked_beef, 1);
            } else
            {
                dropItem(Items.beef, 1);
            }
        }
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
                EntityItem entityItem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(ModItems.buffaloItems, 2 + rand.nextInt(3), ItemBuffaloDrops.hair));
                worldObj.spawnEntityInWorld(entityItem);

                return true;
            }
        }

        return super.interact(player);

    }*/

    @Override
    public EntityBuffalo createChild(EntityAgeable var1)
    {
        return new EntityBuffalo(worldObj);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer player)
    {
        return 5 + worldObj.rand.nextInt(6);
    }

    @Override
    public boolean canMateWith(EntityAnimal animal)
    {
        return animal != this && (animal.getClass() == getClass() && isInLove() && animal.isInLove());
    }
}
