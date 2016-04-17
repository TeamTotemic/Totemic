package totemic_commons.pokefenn.entity.boss;

import java.util.Arrays;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.entity.projectile.EntityInvisArrow;

public class EntityBaykok extends EntityMob implements IBossDisplayData, IRangedAttackMob
{
    public EntityBaykok(World world)
    {
        super(world);
        experienceValue = 65;
        setSize(0.75F, 2.25F);
        setHealth(getMaxHealth());

        ((PathNavigateGround)getNavigator()).setCanSwim(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 12, 30, 40.0F));
        tasks.addTask(5, new EntityAIWander(this, 1.0));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false, false));

        Arrays.fill(equipmentDropChances, 0.0F);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200);
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4);
        getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
        setEquipmentBasedOnDifficulty(difficulty);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase entity, float distanceFactor)
    {
        float velocity = 2.0F + 1.0F * distanceFactor;
        EntityInvisArrow arrow = new EntityInvisArrow(worldObj, this, entity, velocity, 4.5F - worldObj.getDifficulty().getDifficultyId());
        arrow.setDamage(2.0 * distanceFactor + 1.0 + 0.25 * rand.nextGaussian() + 0.4 * worldObj.getDifficulty().getDifficultyId());

        playSound("random.bow", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
        worldObj.spawnEntityInWorld(arrow);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        setCurrentItemOrArmor(0, new ItemStack(ModItems.baykokBow));
    }

    @Override
    protected void dropFewItems(boolean byPlayer, int looting)
    {
        dropItem(ModItems.baykokBow, 1);
        dropItem(Items.bone, 2 + rand.nextInt(7 + looting));
        dropItem(Items.rotten_flesh, rand.nextInt(3 + looting));
        dropItem(Items.arrow, 3 + rand.nextInt(8 + looting));

        if(rand.nextInt(100) < 25 + 4*looting)
            entityDropItem(new ItemStack(Items.skull, 1, 1), 0);
        else
            entityDropItem(new ItemStack(Items.skull, 1, 0), 0);
    }

    @Override
    protected void despawnEntity()
    {
        //No despawning
        entityAge = 0;
    }

    @Override
    protected String getLivingSound()
    {
        return "mob.skeleton.say";
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.skeleton.hurt";
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.skeleton.death";
    }

    @Override
    protected float getSoundPitch()
    {
        return super.getSoundPitch() - 0.15F;
    }
}
