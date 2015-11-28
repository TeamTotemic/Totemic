package totemic_commons.pokefenn.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;
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
    public boolean generate(World world, Random rand, int x, int trying, int z)
    {
        for(int c = 0; c < trying; c++)
        {
            int y = world.getActualHeight() - 1;
            while(world.isAirBlock(x, y, z) && y > 0)
            {
                y--;
            }

            if(!growTree(world, rand, x, y + 1, z))
            {
                trying--;
            }

            x += rand.nextInt(16) - 8;
            z += rand.nextInt(16) - 8;
        }

        return true;
    }

    public boolean growTree(World world, Random rand, int x, int y, int z)
    {
        int treeHeight = rand.nextInt(3) + 7, worldHeight = world.getHeight();
        Block block;

        if(y >= 1 && y + treeHeight + 1 <= worldHeight)
        {
            int xOffset;
            int yOffset;
            int zOffset;

            block = world.getBlock(x, y - 1, z);

            if((block != null && block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, ((BlockSapling) ModBlocks.totemSapling))) && y < worldHeight - treeHeight - 1)
            {
                for(yOffset = y + 1; yOffset <= y + 1 + treeHeight; ++yOffset)
                {
                    byte radius = 1;

                    if(yOffset >= y + 1 + treeHeight - 2)
                    {
                        radius = 2;
                    }

                    if(yOffset >= 0 & yOffset < worldHeight)
                    {
                        for(xOffset = x - radius; xOffset <= x + radius; ++xOffset)
                        {
                            for(zOffset = z - radius; zOffset <= z + radius; ++zOffset)
                            {
                                block = world.getBlock(xOffset, yOffset, zOffset);

                                if(block != null && !(block.isLeaves(world, xOffset, yOffset, zOffset) || block.isAir(world, xOffset, yOffset, zOffset) || block.canBeReplacedByLeaves(world, xOffset, yOffset, zOffset)))
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

                block = world.getBlock(x, y - 1, z);
                if(block == null)
                {
                    return false;
                }
                block.onPlantGrow(world, x, y - 1, z, x, y, z);

                for(yOffset = y - 7 + treeHeight; yOffset <= y + treeHeight; ++yOffset)
                {
                    int var12 = yOffset - (y + treeHeight);
                    int center = 1 - var12 / 3;

                    for(xOffset = x - center; xOffset <= x + center; ++xOffset)
                    {
                        int xPos = xOffset - x, t = xPos >> 31;
                        xPos = (xPos + t) ^ t;

                        for(zOffset = z - center; zOffset <= z + center; ++zOffset)
                        {
                            int zPos = zOffset - z;
                            zPos = (zPos + (t = zPos >> 31)) ^ t;

                            block = world.getBlock(xOffset, yOffset, zOffset);

                            if(((xPos != center | zPos != center) || rand.nextInt(2) != 0 && var12 != 0) && (block == null || block.isLeaves(world, xOffset, yOffset, zOffset) || block.isAir(world, xOffset, yOffset, zOffset) || block.canBeReplacedByLeaves(world, xOffset, yOffset, zOffset)))
                            {
                                this.setBlockAndNotifyAdequately(world, xOffset, yOffset, zOffset, ModBlocks.totemLeaves, 0);
                            }
                        }
                    }
                }

                for(yOffset = 0; yOffset < treeHeight; ++yOffset)
                {
                    block = world.getBlock(x, y + yOffset, z);

                    if(block == null || block.isAir(world, x, y + yOffset, z) || block.isLeaves(world, x, y + yOffset, z) || block.isReplaceable(world, x, y + yOffset, z))
                    {
                        this.setBlockAndNotifyAdequately(world, x, y + yOffset, z, ModBlocks.cedarLog, 0);
                    }
                }

                return true;
            }
        }
        return false;
    }

}

