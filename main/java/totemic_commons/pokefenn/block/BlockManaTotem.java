package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.lib.Strings;
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
}
