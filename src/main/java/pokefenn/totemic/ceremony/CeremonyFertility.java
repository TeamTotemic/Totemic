package pokefenn.totemic.ceremony;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.util.EntityUtil;

public class CeremonyFertility extends Ceremony
{
    public CeremonyFertility(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        int radius = 8;

        if(!world.isRemote && context.getTime() % 30 == 0)
        {
            for(Entity entity: EntityUtil.getEntitiesInRange(EntityLiving.class, world, pos, radius, radius, e -> e instanceof EntityAnimal || e instanceof EntityVillager))
            {
                if(entity instanceof EntityAnimal)
                {
                    EntityAnimal animal = (EntityAnimal) entity;
                    if(animal.getGrowingAge() == 0 && !animal.isInLove())
                    {
                        animal.setInLove(null);
                        break;
                    }
                }
                else
                {
                    EntityVillager villager = (EntityVillager) entity;
                    if(villager.getGrowingAge() == 0 && !villager.getIsWillingToMate(false) && !villager.isMating())
                    {
                        villager.setIsWillingToMate(true);
                        villager.tasks.addTask(0, new EntityAIVillagerFertility(villager));
                        break;
                    }
                }
            }
        }

        if(context.getTime() % 20 == 0)
        {
            for(BlockPos p: BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius)))
            {
                IBlockState state = world.getBlockState(p);
                Block block = state.getBlock();
                if(block instanceof BlockSapling && block != ModBlocks.cedar_sapling)
                {
                    world.setBlockState(p, ModBlocks.cedar_sapling.getDefaultState(), 3);
                    spawnParticles(world, p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5);
                }
            }
        }
    }

    @Override
    public int getEffectTime()
    {
        return SHORT;
    }

    @Override
    public int getMusicPer5()
    {
        return 6;
    }

    private void spawnParticles(World world, double x, double y, double z)
    {
        if(world.isRemote)
        {
            double dx = world.rand.nextGaussian();
            double dy = world.rand.nextGaussian() * 0.5;
            double dz = world.rand.nextGaussian();
            double velY = world.rand.nextGaussian();
            for(int i = 0; i < 10; i++)
                world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x + dx, y + dy, z + dz, 0, velY, 0);
        }
    }

    //Replacement for EntityAIVillagerMate
    private static class EntityAIVillagerFertility extends EntityAIBase
    {
        private final EntityVillager villager;
        private EntityVillager mate;
        private final World world;
        private int matingTimeout;

        public EntityAIVillagerFertility(EntityVillager villagerIn)
        {
            this.villager = villagerIn;
            this.world = villagerIn.world;
            this.setMutexBits(3);
        }

        @Override
        public boolean shouldExecute()
        {
            if(villager.getIsWillingToMate(true))
            {
                EntityVillager mate = this.world.findNearestEntityWithinAABB(EntityVillager.class, this.villager.getEntityBoundingBox().grow(8.0D, 3.0D, 8.0D), this.villager);
                if (mate == null)
                    return false;
                else
                {
                    this.mate = mate;
                    return mate.getGrowingAge() == 0 && mate.getIsWillingToMate(true);
                }
            }
            else
                return false;
        }

        @Override
        public void startExecuting()
        {
            this.matingTimeout = 300;
            this.villager.setMating(true);
        }

        @Override
        public void resetTask()
        {
            this.mate = null;
            this.villager.setMating(false);
        }

        @Override
        public boolean shouldContinueExecuting()
        {
            return this.matingTimeout >= 0 && this.villager.getGrowingAge() == 0 && this.villager.getIsWillingToMate(false);
        }

        @Override
        public void updateTask()
        {
            --this.matingTimeout;
            this.villager.getLookHelper().setLookPositionWithEntity(this.mate, 10.0F, 30.0F);

            if (this.villager.getDistanceSq(this.mate) > 2.25D)
            {
                this.villager.getNavigator().tryMoveToEntityLiving(this.mate, 0.25D);
            }
            else if (this.matingTimeout == 0 && this.mate.isMating())
            {
                this.giveBirth();
            }

            if (this.villager.getRNG().nextInt(35) == 0)
            {
                this.world.setEntityState(this.villager, (byte)12);
            }
        }

        private void giveBirth()
        {
            net.minecraft.entity.EntityAgeable entityvillager = this.villager.createChild(this.mate);
            this.mate.setGrowingAge(6000);
            this.villager.setGrowingAge(6000);
            this.mate.setIsWillingToMate(false);
            this.villager.setIsWillingToMate(false);

            final net.minecraftforge.event.entity.living.BabyEntitySpawnEvent event = new net.minecraftforge.event.entity.living.BabyEntitySpawnEvent(villager, mate, entityvillager);
            if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event) || event.getChild() == null) { return; }
            entityvillager = event.getChild();
            entityvillager.setGrowingAge(-24000);
            entityvillager.setLocationAndAngles(this.villager.posX, this.villager.posY, this.villager.posZ, 0.0F, 0.0F);
            this.world.spawnEntity(entityvillager);
            this.world.setEntityState(entityvillager, (byte)12);
        }
    }
}
