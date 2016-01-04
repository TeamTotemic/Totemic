package totemic_commons.pokefenn.block;

import java.util.List;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 09/12/13
 * Time: 14:35
 */
public class BlockCedarLog extends BlockLog
{

    public BlockCedarLog()
    {
        super();
        setUnlocalizedName(Strings.CEDAR_LOG_NAME);
        setHardness(2F);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item blockId, CreativeTabs creativeTab, List<ItemStack> subTypes)
    {
        subTypes.add(new ItemStack(blockId, 1, 0));
    }
}








