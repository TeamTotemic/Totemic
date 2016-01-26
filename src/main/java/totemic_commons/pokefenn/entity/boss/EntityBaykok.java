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
import totemic_commons.pokefenn.entity.projectile.EntityInvisArrow;

public class EntityBaykok extends EntityMob implements IBossDisplayData, IRangedAttackMob
{
    public EntityBaykok(World world)
    {
        super(world);
        experienceValue = 65;
        setHealth(getMaxHealth());

        ((PathNavigateGround)getNavigator()).setCanSwim(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 10, 30, 40.0F));
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
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
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
        //FIXME: Arrows that are very fast will aim too high and miss on medium distances
        EntityInvisArrow arrow = new EntityInvisArrow(worldObj, this, entity, velocity, 11 - 3 * worldObj.getDifficulty().getDifficultyId());
        arrow.setDamage(3.0 * distanceFactor + 1.0 * rand.nextGaussian() + 0.4 * worldObj.getDifficulty().getDifficultyId());

        playSound("random.bow", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
        worldObj.spawnEntityInWorld(arrow);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        setCurrentItemOrArmor(0, new ItemStack(Items.bow)); //TODO
    }

    @Override
    protected void despawnEntity()
    {
        //No despawning
        entityAge = 0;
    }
}
