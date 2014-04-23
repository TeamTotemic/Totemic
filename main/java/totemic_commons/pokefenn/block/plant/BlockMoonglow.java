package totemic_commons.pokefenn.block.plant;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.plant.IPlantDrain;
import totemic_commons.pokefenn.lib.Strings;

import java.util.ArrayList;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockMoonglow extends BlockBasePlant implements IPlantDrain
{
    public BlockMoonglow()
    {
        setBlockName(Strings.MOONGLOW_NAME);
        setHardness(0);
        maximumStages = 4;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z)
    {
        return EnumPlantType.Crop;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z)
    {
        return world.getBlockMetadata(x, y, z);
    }

    protected Item func_149866_i()
    {
        return null;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        int count = 1;

        if(metadata >= 3)
        {
            count = 2 + world.rand.nextInt(1) + (fortune > 0 ? world.rand.nextInt(fortune + 1) : 0);
        }

        for(int i = 0; i < count; i++)
        {
            ret.add(new ItemStack(ModItems.moonglowSeeds));
        }
        ret.add(new ItemStack(ModItems.subItems, 1, 3));

        return ret;
    }

    @Override
    public int getPlantDrain(World world, int x, int y, int z)
    {
        return !world.isDaytime() ? 5 : 2;
    }
}
