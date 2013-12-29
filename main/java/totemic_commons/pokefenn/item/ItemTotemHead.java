package totemic_commons.pokefenn.item;

import rukalib_commons.pokefenn.item.ItemNormal;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;


public class ItemTotemHead extends ItemNormal {


    public ItemTotemHead(int id) {

        super(id);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOTEM_HEAD_NAME);
        setMaxStackSize(64);
        setCreativeTab(Totemic.tabsTotem);


    }


}
