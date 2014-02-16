package totemic_commons.pokefenn.block;

import net.minecraft.block.BlockSapling;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 12/02/14
 * Time: 12:55
 */
public class BlockTotemSapling extends BlockSapling
{
    public BlockTotemSapling(int id)
    {
        super(id);
        setUnlocalizedName(Strings.TOTEM_SAPLING_NAME);
    }
}
