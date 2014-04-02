package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 09/12/13
 * Time: 14:35
 */
public class BlockTotemWoods extends Block
{


    public BlockTotemWoods()
    {
        super(Material.wood);
        setBlockName(Strings.TOTEM_WOODS_NAME);
        setHardness(1F);
        setCreativeTab(Totemic.tabsTotem);

    }


    @SideOnly(Side.CLIENT)
    private IIcon topAndBot;
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        sideIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.INFUSED_WOOD_SIDE);
        topAndBot = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.INFUSED_WOOD_TOP_AND_BOT);

    }

    /*
    @Override
    public int idDropped(Item par1, Random par2Random, int par3)
    {
        return ModBlocks.totemWoods;
    }
    */

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        int logDirection = meta & 12;
        return logDirection == 0 && (side == 1 || side == 0) ? topAndBot : (logDirection == 4 && (side == 5 || side == 4) ? topAndBot : (logDirection == 8 && (side == 2 || side == 3) ? topAndBot : sideIcon));

    }

    @Override
    public void getSubBlocks(Item blockId, CreativeTabs creativeTab, List subTypes)
    {
        subTypes.add(new ItemStack(blockId, 1, 0));
    }



}








