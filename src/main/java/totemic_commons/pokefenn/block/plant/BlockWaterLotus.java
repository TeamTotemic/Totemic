package totemic_commons.pokefenn.block.plant;

import net.minecraft.block.BlockLilyPad;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.plant.IPlantDrain;
import totemic_commons.pokefenn.lib.Strings;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockWaterLotus extends BlockLilyPad implements IPlantDrain
{
    public BlockWaterLotus()
    {
        setBlockName(Strings.LOTUS_BLOCK_NAME);
        setHardness(0);
    }

    @Override
    public int getRenderType()
    {
        return 6;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        int count = 1;

        if(metadata >= 3)
        {
            count = 2 + world.rand.nextInt(3) + (fortune > 0 ? world.rand.nextInt(fortune + 1) : 0);
        }

        for(int i = 0; i < count; i++)
        {
            ret.add(new ItemStack(ModItems.lotusSeed));
        }

        return ret;
    }

    public int maximumStages = 5;

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
                    if(!world.isDaytime())
                    {
                        world.setBlockMetadataWithNotify(x, y, z, +metadata, 2);
                    }
                }
            }
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
    {
        return EnumPlantType.Water;
    }

    @Override
    public int getPlantDrain(World world, int x, int y, int z)
    {
        return world.isRaining() ? 5 : 2;
    }
}
