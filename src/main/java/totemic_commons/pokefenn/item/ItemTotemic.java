package totemic_commons.pokefenn.item;

import net.minecraft.item.Item;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

public class ItemTotemic extends Item
{
    public ItemTotemic(String name, boolean creativeTab)
    {
        setNoRepair();
        if(!name.isEmpty()) {
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
