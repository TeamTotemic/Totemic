package pokefenn.totemic.block;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.lib.Strings;

public class BlockCedarStairs extends BlockStairs
{
    public BlockCedarStairs(IBlockState modelState)
    {
        super(modelState);
        setRegistryName(Strings.CEDAR_STAIRS_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.CEDAR_STAIRS_NAME);
        setCreativeTab(Totemic.tabsTotem);
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}
