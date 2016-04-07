package totemic_commons.pokefenn.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import totemic_commons.pokefenn.ModBlocks;

public class TotemTreeGeneration extends WorldGenAbstractTree
{
    public TotemTreeGeneration(boolean doNotify)
    {
        super(doNotify);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        return growTree(world, rand, pos);
    }

    public boolean growTree(World world, Random rand, final BlockPos pos)
    {
        final int treeHeight = rand.nextInt(3) + 7;
        if(!canTreeGrow(world, pos, treeHeight))
            return false;

        world.getBlockState(pos.down()).getBlock().onPlantGrow(world, pos.down(), pos);

        for(int curY = pos.getY() - 7 + treeHeight; curY <= pos.getY() + treeHeight; ++curY)
        {
            int var12 = curY - (pos.getY() + treeHeight);
            int radius = 1 - var12 / 3;

            for(int curX = pos.getX() - radius; curX <= pos.getX() + radius; ++curX)
            {
                int xOffset = curX - pos.getX();
                int t = xOffset >> 31;
                xOffset = (xOffset + t) ^ t;

                for(int curZ = pos.getZ() - radius; curZ <= pos.getZ() + radius; ++curZ)
                {
                    int zOffset = curZ - pos.getZ();
                    int u = zOffset >> 31;
                    zOffset = (zOffset + u) ^ u;

                    BlockPos p = new BlockPos(curX, curY, curZ);

                    Block block = world.getBlockState(p).getBlock();

                    if((xOffset != radius || zOffset != radius || rand.nextInt(2) != 0 && var12 != 0)
                            && (block == null || block.isLeaves(world, p) || block.isAir(world, p)))
                    {
                        setBlockAndNotifyAdequately(world, p, ModBlocks.totemLeaves.getDefaultState());
                    }
                }
            }
        }

        for(int yOffset = 0; yOffset < treeHeight; ++yOffset)
        {
            setBlockAndNotifyAdequately(world, pos.up(yOffset), ModBlocks.cedarLog.getDefaultState());
        }

        return true;
    }

    public boolean canTreeGrow(World world, BlockPos pos, int treeHeight)
    {
        if(pos.getY() < 1 || pos.getY() + treeHeight + 1 > world.getHeight())
            return false;

        Block block = world.getBlockState(pos.down()).getBlock();
        if(block == null || !block.canSustainPlant(world, pos.down(), EnumFacing.UP, ModBlocks.totemSapling))
            return false;

        for(int yOffset = pos.getY() + 1; yOffset <= pos.getY() + 1 + treeHeight; ++yOffset)
        {
            int radius = 2;

            if(yOffset >= pos.getY() + 1 + treeHeight - 2)
            {
                radius = 1;
            }

            if(yOffset >= 0 && yOffset < world.getHeight())
            {
                for(int xOffset = pos.getX() - radius; xOffset <= pos.getX() + radius; ++xOffset)
                {
                    for(int zOffset = pos.getZ() - radius; zOffset <= pos.getZ() + radius; ++zOffset)
                    {
                        BlockPos p = new BlockPos(xOffset, yOffset, zOffset);
                        if(!isReplaceable(world, p))
                        {
                            return false;
                        }
                    }
                }
            }
            else
                return false;
        }

        block = world.getBlockState(pos.down()).getBlock();
        if(block == null)
            return false;

        return true;
    }

}

