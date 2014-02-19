package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTotemCauldron;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 19/02/14
 * Time: 19:45
 */
public class BlockTotemCauldron extends BlockTile
{
    public BlockTotemCauldron(int id)
    {
        super(id, Material.rock);
        setUnlocalizedName(Strings.TOTEM_CAULDRON_NAME);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileTotemCauldron();
    }
}
