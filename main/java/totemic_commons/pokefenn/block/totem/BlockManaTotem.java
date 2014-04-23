package totemic_commons.pokefenn.block.totem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.lib.Textures;
import totemic_commons.pokefenn.tileentity.TileTotemMana;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/02/14
 * Time: 19:49
 */
public class BlockManaTotem extends BlockTileTotemic
{
    public BlockManaTotem()
    {
        super(Material.wood);
        setBlockName(Strings.TOTEM_MANA_NAME);
    }

    @SideOnly(Side.CLIENT)
    private IIcon icon;


    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        icon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.MANA_TOTEM);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return icon;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileTotemMana();
    }
}
