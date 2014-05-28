package totemic_commons.pokefenn.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;
import totemic_commons.pokefenn.ModBlocks;

import java.util.Random;

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

        if (y >= 1 && y + treeHeight + 1 <= worldHeight)
        {
            int xOffset;
            int yOffset;
            int zOffset;

            block = world.getBlock(x, y - 1, z);

            if ((block != null && block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, ((BlockSapling) ModBlocks.totemSapling))) && y < worldHeight - treeHeight - 1)
            {
                for (yOffset = y; yOffset <= y + 1 + treeHeight; ++yOffset)
                {
                    byte radius = 1;

                    if (yOffset == y)
                    {
                        radius = 0;
                    }

                    if (yOffset >= y + 1 + treeHeight - 2)
                    {
                        radius = 2;
                    }

                    if (yOffset >= 0 & yOffset < worldHeight)
                    {
                        for (xOffset = x - radius; xOffset <= x + radius; ++xOffset)
                        {
                            for (zOffset = z - radius; zOffset <= z + radius; ++zOffset)
                            {
                                block = world.getBlock(xOffset, yOffset, zOffset);

                                if (block != null && !(block.isLeaves(world, xOffset, yOffset, zOffset) || block.isAir(world, xOffset, yOffset, zOffset) || block.canBeReplacedByLeaves(world, xOffset, yOffset, zOffset)))
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
                if (block == null)
                {
                    return false;
                }
                block.onPlantGrow(world, x, y - 1, z, x, y, z);

                for (yOffset = y - 7 + treeHeight; yOffset <= y + treeHeight; ++yOffset)
                {
                    int var12 = yOffset - (y + treeHeight);
                    int center = 1 - var12 / 3;

                    for (xOffset = x - center; xOffset <= x + center; ++xOffset)
                    {
                        int xPos = xOffset - x, t = xPos >> 31;
                        xPos = (xPos + t) ^ t;

                        for (zOffset = z - center; zOffset <= z + center; ++zOffset)
                        {
                            int zPos = zOffset - z;
                            zPos = (zPos + (t = zPos >> 31)) ^ t;

                            block = world.getBlock(xOffset, yOffset, zOffset);

                            if (((xPos != center | zPos != center) || rand.nextInt(2) != 0 && var12 != 0) && (block == null || block.isLeaves(world, xOffset, yOffset, zOffset) || block.isAir(world, xOffset, yOffset, zOffset) || block.canBeReplacedByLeaves(world, xOffset, yOffset, zOffset)))
                            {
                                this.setBlockAndNotifyAdequately(world, xOffset, yOffset, zOffset, ModBlocks.totemLeaves, 0);
                            }
                        }
                    }
                }

                for (yOffset = 0; yOffset < treeHeight; ++yOffset)
                {
                    block = world.getBlock(x, y + yOffset, z);

                    if (block == null || block.isAir(world, x, y + yOffset, z) || block.isLeaves(world, x, y + yOffset, z) || block.isReplaceable(world, x, y + yOffset, z))
                    {
                        this.setBlockAndNotifyAdequately(world, x, y + yOffset, z, ModBlocks.cedarLog, 0);
                    }
                }

                return true;
            }
        }
        return false;
    }

    /*
    public boolean growTree(World world, Random rand, int x, int y, int z)
    {
        if(y > world.getHeight() - 8) return false;

        int trunkHeight = rand.nextInt(3) + 4;

        int branchStartY = y + trunkHeight - 2;
        for(int yy = y; yy < branchStartY; yy++)
        {
            if(!world.isAirBlock(x, yy, z)) return false;
        }

        for(int yy = branchStartY; yy < branchStartY + 2; yy++)
        {
            for(int xx = x - 1; xx <= x + 1; xx++)
            {
                for(int zz = z - 1; zz <= z + 1; zz++)
                {
                    if(!world.isAirBlock(xx, yy, zz)) return false;
                }
            }
        }

        for(int yy = branchStartY + 2, r = 3; yy < branchStartY + 5; yy++)
        {
            for(int xx = x - r; xx <= x + r; xx++)
            {
                for(int zz = z - r; zz <= z + r; zz++)
                {
                    if(!world.isAirBlock(xx, yy, zz)) return false;
                }
            }
            --r;
        }

        byte branchSizes[] = new byte[4];
        boolean longBranchGenerated = false;
        for(int attempt = 0; attempt < 3; attempt++)
        {
            if((branchSizes[rand.nextInt(4)] = (byte) (rand.nextBoolean() && !longBranchGenerated ? 2 : 1)) == 2)
                longBranchGenerated = true;
        }

        for(int yy = y; yy < y + trunkHeight; yy++)
            world.setBlock(x, yy, z, ModBlocks.totemWoods, 0, 3);

        generateLeaves(world, x, y + trunkHeight, z, rand.nextInt(2) + 1);

        for(int a = 0, xx, zz; a < 4; a++)
        {
            if(branchSizes[a] == 0) continue;
            for(int branch = 0; branch < branchSizes[a]; branch++)
            {
                xx = a == 0 ? x + 1 + branch : a == 1 ? x - 1 - branch : x;
                zz = a == 2 ? z + 1 + branch : a == 3 ? z - 1 - branch : z;
                world.setBlock(xx, y + trunkHeight - (branchSizes[a] == 1 ? 1 : 2 - branch), zz, ModBlocks.totemWoods, a <= 1 ? 4 : 8, 3);
                if(branch == 0)
                    generateLeaves(world, xx, y + trunkHeight, zz, rand.nextInt(2) + 1);
            }
        }

        for(int yy = y + trunkHeight + 1; yy <= y + trunkHeight + 2; yy++)
        {
            for(int xx = x - 4; xx <= x + 4; xx++)
            {
                for(int zz = z - 4; zz <= z + 4; zz++)
                {
                    if(world.getBlock(xx - 1, yy - 1, zz) == ModBlocks.totemLeaves && world.getBlock(xx + 1, yy - 1, zz) == ModBlocks.totemLeaves && world.getBlock(xx, yy - 1, zz - 1) == ModBlocks.totemLeaves && world.getBlock(xx, yy - 1, zz + 1) == ModBlocks.totemLeaves)
                    {
                        world.setBlock(xx, yy, zz, ModBlocks.totemLeaves, 0, 3);
                    }
                }
            }
        }

        return true;
    }

    private void generateLeaves(World world, int x, int y, int z, int sideSize)
    {
        for(int xx = x - 2, xDiff, zDiff; xx <= x + 2; xx++)
        {
            for(int zz = z - 2; zz <= z + 2; zz++)
            {
                xDiff = Math.abs(xx - x);
                zDiff = Math.abs(zz - z);

                if(((xDiff <= 1 && zDiff <= 1) || (xDiff == 2 && zDiff <= sideSize) || (xDiff <= sideSize && zDiff == 2)) && world.isAirBlock(xx, y, zz))
                {
                    world.setBlock(xx, y, zz, ModBlocks.totemLeaves, 0, 3);
                }
            }
        }
    }
    */


}

