package totemic_commons.pokefenn.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import totemic_commons.pokefenn.ModBlocks;

public class TotemTreeGeneration extends WorldGenerator
{

    public TotemTreeGeneration(boolean doNotify)
    {
        super(doNotify);
    }

    public TotemTreeGeneration()
    {
        super();
    }


    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        /*for(int c = 0; c < y; c++)
        {
            BlockPos p = new BlockPos(pos.getX(), c, pos.getZ());
            int h = world.getActualHeight() - 1;
            while(world.isAirBlock(pos) && h > 0)
            {
                h--;
            }

            if(!growTree(world, rand, x, y + 1, z))
            {
                trying--;
            }

            x += rand.nextInt(16) - 8;
            z += rand.nextInt(16) - 8;
        }*/
        //FIXME: Weird logic

        return true;
    }

    public boolean growTree(World world, Random rand, BlockPos pos)
    {
        final int treeHeight = rand.nextInt(3) + 7, worldHeight = world.getHeight();
        Block block;

        if(pos.getY() >= 1 && pos.getY() + treeHeight + 1 <= worldHeight)
        {
            int xOffset;
            int yOffset;
            int zOffset;

            block = world.getBlockState(pos.down()).getBlock();

            if((block != null && block.canSustainPlant(world, pos.down(), EnumFacing.UP, ((BlockSapling) ModBlocks.totemSapling))) && pos.getY() < worldHeight - treeHeight - 1)
            {
                for(yOffset = pos.getY() + 1; yOffset <= pos.getY() + 1 + treeHeight; ++yOffset)
                {
                    byte radius = 1;

                    if(yOffset >= pos.getY() + 1 + treeHeight - 2)
                    {
                        radius = 2;
                    }

                    if(yOffset >= 0 & yOffset < worldHeight)
                    {
                        for(xOffset = pos.getX() - radius; xOffset <= pos.getX() + radius; ++xOffset)
                        {
                            for(zOffset = pos.getZ() - radius; zOffset <= pos.getZ() + radius; ++zOffset)
                            {
                                BlockPos p = new BlockPos(xOffset, yOffset, zOffset);
                                block = world.getBlockState(p).getBlock();

                                if(block != null && !(block.isLeaves(world, p) || block.isAir(world, p) || block.canBeReplacedByLeaves(world, p)))
                                {
                                    return false;
                                }
                            }
                        }
                    } else
                    {
                        return false;
                    }
                }

                block = world.getBlockState(pos.down()).getBlock();
                if(block == null)
                {
                    return false;
                }
                block.onPlantGrow(world, pos.down(), pos);

                for(yOffset = pos.getY() - 7 + treeHeight; yOffset <= pos.getY() + treeHeight; ++yOffset)
                {
                    int var12 = yOffset - (pos.getY() + treeHeight);
                    int center = 1 - var12 / 3;

                    for(xOffset = pos.getX() - center; xOffset <= pos.getX() + center; ++xOffset)
                    {
                        int xPos = xOffset - pos.getX(), t = xPos >> 31;
                        xPos = (xPos + t) ^ t;

                        for(zOffset = pos.getZ() - center; zOffset <= pos.getZ() + center; ++zOffset)
                        {
                            int zPos = zOffset - pos.getZ();
                            zPos = (zPos + (t = zPos >> 31)) ^ t;

                            BlockPos p = new BlockPos(xOffset, yOffset, zOffset);

                            block = world.getBlockState(p).getBlock();

                            if(((xPos != center | zPos != center) || rand.nextInt(2) != 0 && var12 != 0) && (block == null || block.isLeaves(world, p) || block.isAir(world, p) || block.canBeReplacedByLeaves(world, p)))
                            {
                                this.setBlockAndNotifyAdequately(world, p, ModBlocks.totemLeaves.getDefaultState());
                            }
                        }
                    }
                }

                for(yOffset = 0; yOffset < treeHeight; ++yOffset)
                {
                    block = world.getBlockState(pos.up(yOffset)).getBlock();

                    if(block == null || block.isAir(world, pos.up(yOffset)) || block.isLeaves(world, pos.up(yOffset)) || block.isReplaceable(world, pos.up(yOffset)))
                    {
                        this.setBlockAndNotifyAdequately(world, pos.up(yOffset), ModBlocks.cedarLog.getDefaultState());
                    }
                }

                return true;
            }
        }
        return false;
    }

}

