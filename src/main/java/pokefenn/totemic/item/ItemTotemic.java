package pokefenn.totemic.item;

import net.minecraft.item.Item;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.lib.Strings;

public class ItemTotemic extends Item
{
    public ItemTotemic(String name, boolean creativeTab)
    {
        if(!name.isEmpty())
        {
            setRegistryName(name);
            setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
        }
        if(creativeTab)
            setCreativeTab(Totemic.tabsTotem);
    }

    public ItemTotemic(String name)
    {
        this(name, true);
    }
}
