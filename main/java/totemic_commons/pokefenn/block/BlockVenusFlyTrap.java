package totemic_commons.pokefenn.block;

import rukalib_commons.pokefenn.block.BlockNormal;
import net.minecraft.block.material.Material;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 19/11/13
 * Time: 14:58
 */
public class BlockVenusFlyTrap extends BlockNormal {


    public BlockVenusFlyTrap(int id) {

        super(id, Material.wood);
        setUnlocalizedName(Strings.VENUS_FLY_TRAP_NAME);
        setHardness(1F);
        setCreativeTab(Totemic.tabsTotem);

    }
}
