package totemic_commons.pokefenn.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
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
    public BlockManaTotem(int id)
    {
        super(id, Material.wood);
        setUnlocalizedName(Strings.TOTEM_MANA_NAME);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileTotemMana();
    }

    @SideOnly(Side.CLIENT)
    private Icon icon;


    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister register)
    {
       icon = register.registerIcon(Textures.TEXTURE_LOCATION + ":" + Textures.MANA_TOTEM);

    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int side, int meta)
    {
        return icon;
    }
}
