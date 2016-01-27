package totemic_commons.pokefenn.item.equipment.weapon;

import net.minecraft.item.ItemBow;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

public class ItemBaykokBow extends ItemBow
{
    public ItemBaykokBow()
    {
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.BAYKOK_BOW_NAME);
        setCreativeTab(Totemic.tabsTotem);
    }
}
