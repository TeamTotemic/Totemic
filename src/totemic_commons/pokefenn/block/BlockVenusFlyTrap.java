package totemic_commons.pokefenn.block;

import hinalib_commons.pokefenn.block.BlockNormal;
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


    public BlockVenusFlyTrap(int id){

        super(id, Material.wood);
        this.setUnlocalizedName(Strings.VENUS_FLY_TRAP_NAME);
        this.setHardness(1F);
        this.setCreativeTab(Totemic.tabsTotem);



    }
}
