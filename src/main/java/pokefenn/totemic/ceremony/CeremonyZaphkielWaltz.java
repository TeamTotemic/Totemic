package pokefenn.totemic.ceremony;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.util.EntityUtil;

public class CeremonyZaphkielWaltz extends Ceremony
{

    public CeremonyZaphkielWaltz(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        int radius = 6;

        if(!world.isRemote && context.getTime() % 20 == 0)
        {
            for(EntityItem entity : EntityUtil.getEntitiesInRange(EntityItem.class, world, pos, radius, radius))
            {
                EntityItem item = entity;
                if(item.getItem().getItem() == Items.EGG)
                {
                    if(world.rand.nextInt(4) == 0)
                    {
                        EntityChicken chicken = new EntityChicken(world);
                        chicken.setPosition(entity.posX, entity.posY, entity.posZ);
                        world.spawnEntity(chicken);
                        if(item.getItem().getCount() == 1)
                            item.setDead();
                        else
                        {
                            ItemStack stack = item.getItem().copy();
                            stack.shrink(1);
                            item.setItem(stack);
                        }
                    }
                }
            }
        }

        if(context.getTime() % 5 == 0)
        {
            for(int i = -radius; i <= radius; i++)
                for(int j = -radius; j <= radius; j++)
                    for(int k = -radius; k <= radius; k++)
                    {
                        BlockPos p = pos.add(i, j, k);
                        IBlockState state = world.getBlockState(p);
                        Block block = state.getBlock();
                        if((block instanceof IGrowable || block instanceof IPlantable) && block.getTickRandomly())
                        {
                            if(world.rand.nextInt(4) < 3)
                            {
                                if(!world.isRemote)
                                    block.updateTick(world, p, state, world.rand);
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
