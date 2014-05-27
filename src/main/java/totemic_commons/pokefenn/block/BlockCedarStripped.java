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
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;

import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockCedarStripped extends BlockLog
{
    public BlockCedarStripped()
    {
        super();
        setBlockName(Strings.RED_CEDAR_STRIPPED_NAME);
        setHardness(2F);
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
        sideIcon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + "cedarLogStrippedSide");
        topAndBot = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + "cedarLogStrippedTop");

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
