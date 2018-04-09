package pokefenn.totemic.ceremony;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
                if(entity instanceof EntityAnimal && ((EntityAnimal) entity).getGrowingAge() == 0 && !((EntityAnimal) entity).isInLove())
                {
                    EntityAnimal animal = (EntityAnimal) entity;
                    animal.setInLove(null);
                    break;
                }
                else
                {
                    //TODO: figure out how to make villagers mate
                    /*
                    EntityVillager villager = (EntityVillager) entity;
                    villager.setIsWillingToMate(true);
                    villager.tasks.addTask(0, new EntityAIVillagerMate(villager));
                    villager.setMating(true);
                    */
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
}
