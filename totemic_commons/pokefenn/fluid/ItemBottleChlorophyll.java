package totemic_commons.pokefenn.fluid;

import net.minecraftforge.fluids.ItemFluidContainer;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 17/12/13
 * Time: 20:45
 */
public class ItemBottleChlorophyll extends ItemFluidContainer {

    public ItemBottleChlorophyll(int id){

        super(id - 256);
        setCreativeTab(Totemic.tabsTotem);
        setUnlocalizedName(Strings.BOTTLE_CHLOROPHYLL_NAME);
        setMaxStackSize(64);

    }


}
