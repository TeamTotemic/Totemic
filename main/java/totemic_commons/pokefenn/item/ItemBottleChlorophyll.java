package totemic_commons.pokefenn.item;

import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 17/12/13
 * Time: 20:45
 */
public class ItemBottleChlorophyll extends ItemTotemic
{

    public ItemBottleChlorophyll()
    {

        super();
        setCreativeTab(Totemic.tabsTotem);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.BOTTLE_CHLOROPHYLL_NAME);
        setMaxStackSize(16);

    }


}
