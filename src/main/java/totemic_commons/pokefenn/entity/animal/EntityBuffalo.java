package totemic_commons.pokefenn.entity.animal;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.item.ItemBuffaloDrops;

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
        tasks.addTask(1, new EntityAIPanic(this, 2.0D));
        tasks.addTask(2, new EntityAIMate(this, 1.0D));
        tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.WHEAT, false));
        tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        tasks.addTask(5, new EntityAIWander(this, 1.0D));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15);
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
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return null;
    }

    @Override
    protected void dropFewItems(boolean hitByPlayer, int looting)
    {
        int j = rand.nextInt(5) + rand.nextInt(1 + looting);
        for(int k = 0; k < j; ++k)
        {
            entityDropItem(new ItemStack(ModItems.buffalo_items, 1, ItemBuffaloDrops.Type.hide.ordinal()), 0F);
            if(rand.nextInt(3) < 2)
                entityDropItem(new ItemStack(ModItems.buffalo_items, 1, ItemBuffaloDrops.Type.teeth.ordinal()), 0F);
        }

        j = rand.nextInt(5) + 1 + rand.nextInt(2 + 2*looting);
        for(int k = 0; k < j; ++k)
        {
            if(isBurning())
            {
                dropItem(ModItems.cooked_buffalo_meat, 1);
            }
            else
            {
                dropItem(ModItems.buffalo_meat, 1);
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
        return 5 + world.rand.nextInt(6);
    }
}
