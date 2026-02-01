package totemic_commons.pokefenn.entity.animal;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityBaldEagle extends EntityTameable
{
    public EntityBaldEagle(World world)
    {
        super(world);
        setSize(0.6F, 1.0F);
        tasks.addTask(0, new EntityAIPanic(this, 1.25D));
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIMate(this, 1.0D));
        tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(3, this.aiSit);
        // tasks.addTask(3, new EntityAIFollowOwnerFlying(this, 1.0D, 5.0F, 1.0F));
        // tasks.addTask(3, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
        // tasks.addTask(4, new EntityAIFollow(this, 1.0D, 3.0F, 7.0F));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        // getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        // getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.4);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
    }

    @Override
    public float getEyeHeight()
    {
        return height * 0.6F;
    }

    

    @Override
    public boolean interact(EntityPlayer player)
    {
        ItemStack stack = player.getHeldItem();
        if(!isTamed() && stack.getItem() == Items.fish)
        {
            if(!player.capabilities.isCreativeMode)
                --stack.stackSize;
            if(stack.stackSize <= 0)
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

            // TODO: play sound

            if(!worldObj.isRemote)
            {
                if(rand.nextInt(10) == 0)
                {
                    setTamed(true);
                    func_152115_b(player.getUniqueID().toString()); // sets the owner
                    playTameEffect(true);
                    worldObj.setEntityState(this, (byte)7);
                }
                else
                {
                    playTameEffect(false);
                    worldObj.setEntityState(this, (byte)6);
                }
            }

            return true;
        }
        else
        {
            if(!worldObj.isRemote && !isFlying() && isTamed() && func_152114_e(player)) // func_152114_e checks if the player is the owner
            {
                aiSit.setSitting(!this.isSitting());
            }

            return super.interact(player);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack)
    {
        return stack.getItem() == Items.fish && stack.getItemDamage() == ItemFishFood.FishType.SALMON.func_150976_a();
    }

    

    @Override
    protected void fall(float distance)
    { }

    @Override
    protected void updateFallState(double distanceFallenThisTick, boolean isOnGround)
    { }

    @Override
    public boolean canMateWith(EntityAnimal otherAnimal)
    {
        if(otherAnimal == this)
            return false;
        else if(!isTamed())
            return false;
        else if(!(otherAnimal instanceof EntityBaldEagle))
            return false;
        else
        {
            EntityBaldEagle otherEagle = (EntityBaldEagle) otherAnimal;
            if(!otherEagle.isTamed())
                return false;
            else if(otherEagle.isSitting())
                return false;
            else
                return isInLove() && otherEagle.isInLove();
        }
    }

    @Override
    public EntityAgeable createChild(EntityAgeable mate)
    {
        EntityBaldEagle child = new EntityBaldEagle(worldObj);

        String ownerUuid = func_152113_b();
        if(ownerUuid != null && ownerUuid.trim().length() > 0)
        {
            child.func_152115_b(ownerUuid);
            child.setTamed(true);
        }

        return child;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if(isEntityInvulnerable())
            return false;
        else
        {
            if(this.aiSit != null)
                this.aiSit.setSitting(false);

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean isFlying()
    {
        return !this.onGround;
    }
}
