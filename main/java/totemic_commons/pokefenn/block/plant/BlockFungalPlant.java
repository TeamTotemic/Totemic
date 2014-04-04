package totemic_commons.pokefenn.block.plant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.lib.Strings;

import java.util.ArrayList;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockFungalPlant extends BlockBasePlant implements IPlantDrain
{
    public BlockFungalPlant()
    {
        setBlockName(Strings.FUNGAL_PLANT_BLOCK_NAME);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        int count = 1;

        if (metadata >= 3)
        {
            count = 2 + world.rand.nextInt(1) + (fortune > 0 ? world.rand.nextInt(fortune + 1) : 0);
        }

        for (int i = 0; i < count; i++)
        {
            ret.add(new ItemStack(ModItems.fungusSpore));
        }

        return ret;
    }


    @Override
    public int getPlantDrain(World world, int x, int y, int z)
    {
        return world.getBlock(x, -y, z) == Blocks.mycelium && !world.isDaytime() ? 6 : 2;
    }

    public int getRenderType()
    {
        return 1;
    }

    protected boolean canPlaceBlockOn(Block block)
    {
        return block == Blocks.mycelium;
    }


}
