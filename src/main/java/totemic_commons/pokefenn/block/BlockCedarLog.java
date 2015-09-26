package totemic_commons.pokefenn.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Resources;
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
        setBlockName(Strings.CEDAR_LOG_NAME);
        setHardness(2F);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public int damageDropped(int meta)
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
    protected IIcon getTopIcon(int meta)
    {
        return topAndBot;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected IIcon getSideIcon(int meta)
    {
        return sideIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item blockId, CreativeTabs creativeTab, List subTypes)
    {
        subTypes.add(new ItemStack(blockId, 1, 0));
    }
}








