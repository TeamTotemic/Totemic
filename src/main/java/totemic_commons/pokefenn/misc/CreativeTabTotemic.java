package totemic_commons.pokefenn.misc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import totemic_commons.pokefenn.ModItems;

public class CreativeTabTotemic extends CreativeTabs
{

    public CreativeTabTotemic(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

    @Override
    public Item getTabIconItem()
    {
        return ModItems.totemWhittlingKnife;
    }


}
