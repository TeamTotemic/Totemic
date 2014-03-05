package totemic_commons.pokefenn.item;

import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 19/11/13
 * Time: 12:30
 */
public class ItemVenusFlyTrapSeed extends ItemNormal
{


    public ItemVenusFlyTrapSeed()
    {

        super();
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.VENUS_FLY_TRAP_SEED_NAME);
        setMaxStackSize(64);
        setCreativeTab(Totemic.tabsTotem);

    }


}
