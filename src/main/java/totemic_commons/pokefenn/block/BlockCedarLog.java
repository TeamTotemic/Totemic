package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.lib.Strings;

import java.util.List;

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
        setBlockName(Strings.CEDAR_LOG_NAME);
        setHardness(2F);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public int damageDropped(int p_149692_1_)
    {
        return 0;
    }


    @SideOnly(Side.CLIENT)
    private IIcon topAndBot;
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        sideIcon = register.registerIcon(Resources.TEXTURE_LOCATION + ":" + Resources.INFUSED_WOOD_SIDE);
        topAndBot = register.registerIcon(Resources.TEXTURE_LOCATION + ":" + Resources.INFUSED_WOOD_TOP_AND_BOT);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        int logDirection = meta & 12;
        return logDirection == 0 && (side == 1 || side == 0) ? topAndBot : (logDirection == 4 && (side == 5 || side == 4) ? topAndBot : (logDirection == 8 && (side == 2 || side == 3) ? topAndBot : sideIcon));

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item blockId, CreativeTabs creativeTab, List subTypes)
    {
        subTypes.add(new ItemStack(blockId, 1, 0));
    }
}








