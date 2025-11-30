package totemic_commons.pokefenn.entity.boss;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.entity.projectile.EntityInvisArrow;

public class EntityBaykok extends EntityMob implements IBossDisplayData, IRangedAttackMob
{
    // private final BossInfoServer bossInfo = (BossInfoServer)new BossInfoServer(getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS).setDarkenSky(true);

    public EntityBaykok(World world)
    {
        super(world);
        experienceValue = 65;
        setSize(0.55F, 2.25F);
        setHealth(getMaxHealth());

        getNavigator().setCanSwim(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 12, 30, 40.0F));
        tasks.addTask(5, new EntityAIWander(this, 1.0));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));

        //Even if set to 0.0, with looting there is still a slight chance of dropping
        Arrays.fill(equipmentDropChances, -1.0F);
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
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
        float velocity = 2.0F + 1.0F * distanceFactor;
        float inaccuracy = 4.5F - worldObj.difficultySetting.getDifficultyId();
        EntityInvisArrow arrow = new EntityInvisArrow(worldObj, this, target, velocity, inaccuracy);
        arrow.setDamage(2.0 * distanceFactor + 1.0 + 0.25 * rand.nextGaussian() + 0.4 * worldObj.difficultySetting.getDifficultyId());

        playSound("random.bow", 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));
        worldObj.spawnEntityInWorld(arrow);
    }

    // This is a misnomer in MCP - the method is called whenever the entity is spawned, egg or not
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
    {
        data = super.onSpawnWithEgg(data);
        addRandomArmor();
        enchantEquipment();
        return data;
    }

    @Override
    protected void addRandomArmor()
    {
        this.setCurrentItemOrArmor(0, new ItemStack(/* TODO ModItems.baykok_bow */ Items.bow));
    }

    @Override
    protected Item getDropItem()
    {
        return Items.bow; // TODO: Baykok's Bow
    }

    @Override
    protected void dropFewItems(boolean hitByPlayer, int looting)
    {
        dropItem(Items.bow, 1); // TODO: Baykok's Bow

        int boneCount = 2 + rand.nextInt(6 + looting);
        for(int i = 0; i < boneCount; i++)
            dropItem(Items.bone, 1);

        int arrowCount = 3 + rand.nextInt(7 + looting);
        for(int i = 0; i < arrowCount; i++)
            dropItem(Items.arrow, 1);

        int rottenFleshCount = rand.nextInt(2 + looting);
        for(int i = 0; i < rottenFleshCount; i++)
            dropItem(Items.rotten_flesh, 1);

        boolean dropWitherSkull = rand.nextDouble() < 0.25 + 0.05 * looting;
        entityDropItem(new ItemStack(Items.skull, 1, dropWitherSkull ? 1 : 0), 0.0F);
    }

    @Override
    protected void despawnEntity()
    {
        //No despawning
        this.entityAge = 0;
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
    protected void func_145780_a(int x, int y, int z, Block blockIn)
    {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }

    @Override
    protected float getSoundPitch()
    {
        return super.getSoundPitch() - 0.15F;
    }
}
