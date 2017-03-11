package totemic_commons.pokefenn.entity.boss;

import java.util.Arrays;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.entity.projectile.EntityInvisArrow;

public class EntityBaykok extends EntityMob implements IRangedAttackMob
{
    private final BossInfoServer bossInfo = (BossInfoServer)new BossInfoServer(getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS).setDarkenSky(true);

    public EntityBaykok(World world)
    {
        super(world);
        experienceValue = 65;
        setSize(0.55F, 2.25F);
        setHealth(getMaxHealth());

        ((PathNavigateGround) getNavigator()).setCanSwim(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(2, new EntityAIAttackRanged(this, 1.0, 12, 30, 40.0F));
        tasks.addTask(5, new EntityAIWander(this, 1.0));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false, false));

        Arrays.fill(inventoryHandsDropChances, 0.0F);
        Arrays.fill(inventoryArmorDropChances, 0.0F);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        setEquipmentBasedOnDifficulty(difficulty);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    protected void updateAITasks()
    {
        super.updateAITasks();
        bossInfo.setPercent(getHealth() / getMaxHealth());
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
        EntityInvisArrow arrow = new EntityInvisArrow(world, this);
        double dx = target.posX - this.posX;
        double dy = target.getEntityBoundingBox().minY + target.height / 3.0F - arrow.posY;
        double dz = target.posZ - this.posZ;
        double xzdist = MathHelper.sqrt(dx*dx + dz*dz);
        float velocity = 2.0F + 1.0F * distanceFactor;
        float inaccuracy = 4.5F - world.getDifficulty().getDifficultyId();
        arrow.setThrowableHeading(dx, dy + 0.125 * xzdist, dz, velocity, inaccuracy);
        arrow.setDamage(2.0 * distanceFactor + 1.0 + 0.25 * rand.nextGaussian() + 0.4 * world.getDifficulty().getDifficultyId());

        playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
        world.spawnEntity(arrow);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModItems.baykokBow));
    }

    @Override
    protected void dropFewItems(boolean byPlayer, int looting)
    {
        dropItem(ModItems.baykokBow, 1);
        dropItem(Items.BONE, 2 + rand.nextInt(7 + looting));
        dropItem(Items.ROTTEN_FLESH, rand.nextInt(3 + looting));
        dropItem(Items.ARROW, 3 + rand.nextInt(8 + looting));

        if(rand.nextInt(100) < 25 + 4*looting)
            entityDropItem(new ItemStack(Items.SKULL, 1, 1), 0);
        else
            entityDropItem(new ItemStack(Items.SKULL, 1, 0), 0);
    }

    @Override
    protected void despawnEntity()
    {
        //No despawning
        entityAge = 0;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SKELETON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound()
    {
        return SoundEvents.ENTITY_SKELETON_HURT;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SKELETON_DEATH;
    }

    @Override
    protected float getSoundPitch()
    {
        return super.getSoundPitch() - 0.15F;
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        bossInfo.removePlayer(player);
    }
}
