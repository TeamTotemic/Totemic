package totemic_commons.pokefenn.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockCedarStripped extends BlockLog
{
    public BlockCedarStripped()
    {
        setUnlocalizedName(Strings.RED_CEDAR_STRIPPED_NAME);
        setHardness(1.5F);
        setCreativeTab(Totemic.tabsTotem);
        setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
    {
        if(!world.isRemote)
        {
            if(random.nextInt(20) == 0) //about once every 15-20 minutes
            {
                Material mat = world.getBlockState(pos.down()).getBlock().getMaterial();
                if(mat == Material.ground || mat == Material.grass)
                {
                    world.setBlockState(pos, getDefaultState());
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item blockId, CreativeTabs creativeTab, List<ItemStack> subTypes)
    {
        subTypes.add(new ItemStack(blockId, 1, 0));
    }

}
