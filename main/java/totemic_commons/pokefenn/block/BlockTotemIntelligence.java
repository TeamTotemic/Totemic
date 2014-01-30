package totemic_commons.pokefenn.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ITotemBlock;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 20:20
 */
public class BlockTotemIntelligence extends BlockTile implements ITotemBlock
{

    public BlockTotemIntelligence(int id)
    {
        super(id,Material.wood);
        setUnlocalizedName(Strings.TOTEM_INTELLIGENCE_NAME);

    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileTotemIntelligence();
    }
}
