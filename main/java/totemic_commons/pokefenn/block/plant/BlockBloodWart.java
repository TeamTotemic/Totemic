package totemic_commons.pokefenn.block.plant;

import net.minecraft.block.BlockNetherWart;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.lib.Strings;

import java.util.ArrayList;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockBloodWart extends BlockNetherWart implements IPlantDrain
{

    public BlockBloodWart()
    {
        setBlockName(Strings.BLOODWART_BLOCK_NAME);
        setHardness(0);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        int count = 1;

        if(metadata >= 3)
        {
            count = 2 + world.rand.nextInt(2) + (fortune > 0 ? world.rand.nextInt(fortune + 1) : 0);
        }

        for(int i = 0; i < count; i++)
        {
            ret.add(new ItemStack(ModItems.bloodwart));
        }

        return ret;
    }

    @Override
    public int getPlantDrain(World world, int x, int y, int z)
    {
        return world.provider.dimensionId == -1 ? 8 : 2;
    }
}
