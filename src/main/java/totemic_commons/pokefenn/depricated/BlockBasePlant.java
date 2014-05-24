package totemic_commons.pokefenn.depricated;

import net.minecraft.block.BlockCrops;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockBasePlant extends BlockCrops
{

    public int maximumStages = 5;

    public static void drainPlant(World world, int x, int y, int z)
    {
        world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) - 1, 2);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        if(!world.isRemote)
        {
            if(metadata < maximumStages)
            {
                if(random.nextInt(10) == 1)
                {
                    if(world.isDaytime())
                    {
                        world.setBlockMetadataWithNotify(x, y, z, +metadata, 2);
                    }
                }
            }
        }
    }


}
