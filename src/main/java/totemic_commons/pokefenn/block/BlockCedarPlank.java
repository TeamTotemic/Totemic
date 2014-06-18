package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockCedarPlank extends Block
{
    public BlockCedarPlank()
    {
        super(Material.wood);
        setBlockName(Strings.RED_CEDAR_PLANK_NAME);
        setHardness(2F);
    }

    @SideOnly(Side.CLIENT)
    private IIcon cedarPlank;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        cedarPlank = register.registerIcon(Resources.TEXTURE_LOCATION + ":" + "cedarPlank");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return cedarPlank;
    }

}
