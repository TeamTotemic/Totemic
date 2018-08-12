package pokefenn.totemic.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import pokefenn.totemic.lib.Strings;

public class BlockCedarStairs extends BlockStairs
{
    public BlockCedarStairs(IBlockState modelState)
    {
        super(modelState);
        setRegistryName(Strings.CEDAR_STAIRS_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.CEDAR_STAIRS_NAME);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}
