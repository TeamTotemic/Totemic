package totemic_commons.pokefenn.entity.animal;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.item.ItemBuffaloDrops;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityBuffalo extends EntityAnimal
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
    protected String getLivingSound()
    {
        return "mob.cow.say";
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
        this.playSound("mob.cow.step", 0.15F, 1.0F);
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();
        Random rand = new Random();

        if(itemstack != null)
        {
            if(itemstack.getItem() == Items.bucket && !par1EntityPlayer.capabilities.isCreativeMode)
            {
                if(itemstack.stackSize-- == 1)
                {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
                } else if(!par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Items.milk_bucket)))
                {
                    par1EntityPlayer.dropPlayerItemWithRandomChoice(new ItemStack(Items.milk_bucket, 1, 0), false);
                }

                return true;
            }

            if(!isSheared && itemstack.getItem() instanceof ItemShears)
            {
                itemstack.damageItem(1, par1EntityPlayer);
                EntityItem entityItem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(ModItems.buffaloItems, 2 + rand.nextInt(3), ItemBuffaloDrops.hair));
                worldObj.spawnEntityInWorld(entityItem);
            }
        }

        return super.interact(par1EntityPlayer);

    }

    @Override
    public EntityAgeable createChild(EntityAgeable var1)
    {
        return new EntityBuffalo(worldObj);
    }

    @Override
    protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
    {
        return 4 + worldObj.rand.nextInt(6);
    }
}
