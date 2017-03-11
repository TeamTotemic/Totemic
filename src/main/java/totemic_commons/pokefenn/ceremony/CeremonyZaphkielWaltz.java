package totemic_commons.pokefenn.ceremony;

import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.util.EntityUtil;

public class CeremonyZaphkielWaltz extends Ceremony
{

    public CeremonyZaphkielWaltz(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, int time)
    {
        int radius = 6;

        if(!world.isRemote && world.getTotalWorldTime() % 20L == 0)
        {
            for(EntityItem entity : EntityUtil.getEntitiesInRange(EntityItem.class, world, pos, radius, radius))
            {
                EntityItem item = entity;
                if(item.getEntityItem().getItem() == Items.EGG)
                {
                    if(world.rand.nextInt(4) == 0)
                    {
                        EntityChicken chicken = new EntityChicken(world);
                        chicken.setPosition(entity.posX, entity.posY, entity.posZ);
                        world.spawnEntity(chicken);
                        if(item.getEntityItem().getCount() == 1)
                            item.setDead();
                        else
                        {
                            ItemStack stack = item.getEntityItem().copy();
                            stack.shrink(1);
                            item.setEntityItemStack(stack);
                        }
                    }
                }
            }
        }

        if(world.getTotalWorldTime() % 5L == 0)
        {
            for(int i = -radius; i <= radius; i++)
                for(int j = -radius; j <= radius; j++)
                    for(int k = -radius; k <= radius; k++)
                    {
                        BlockPos p = pos.add(i, j, k);
                        IBlockState s = world.getBlockState(p);
                        if(s.getBlock() == Blocks.SAPLING)
                        {
                            world.setBlockState(p, ModBlocks.cedarSapling.getDefaultState(), 3);
                            spawnParticles(world, p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5);
                        }
                        else if(s.getBlock() instanceof IGrowable && s.getBlock().getTickRandomly())
                        {
                            if(world.rand.nextInt(4) < 3)
                            {
                                if(!world.isRemote)
                                    s.getBlock().updateTick(world, p, world.getBlockState(p), world.rand);
                                spawnParticles(world, p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5);
                            }
                        }
                    }
        }
    }

    @Override
    public int getEffectTime()
    {
        return LONG;
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
            world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x + dx, y + dy, z + dz, 0, velY, 0);
        }
    }
}
