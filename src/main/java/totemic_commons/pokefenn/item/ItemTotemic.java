package totemic_commons.pokefenn.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/01/14
 * Time: 12:35
 */
public class ItemTotemic extends Item
{
    public ItemTotemic(String name, boolean creativeTab)
    {
        setNoRepair();
        if(!name.isEmpty())
            setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
        if(creativeTab)
            setCreativeTab(Totemic.tabsTotem);
    }

    public ItemTotemic(String name)
    {
        this(name, true);
    }
}
