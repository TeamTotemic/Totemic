package totemic_commons.pokefenn.item;

import net.minecraft.item.ItemFood;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

public class ItemTotemicFood extends ItemFood
{
    public ItemTotemicFood(String name, int amount, float saturation, boolean feedsWolf)
    {
        super(amount, saturation, feedsWolf);
        if(!name.isEmpty())
            setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
        setCreativeTab(Totemic.tabsTotem);
    }
}
