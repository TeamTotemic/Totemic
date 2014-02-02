package totemic_commons.pokefenn.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import totemic_commons.pokefenn.Totemic;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/01/14
 * Time: 12:33
 */
public abstract class BlockTile extends BlockContainer
{
    public BlockTile(int id, Material material)
    {

        super(id, material);
        setHardness(2);
        setCreativeTab(Totemic.tabsTotem);

    }

}
