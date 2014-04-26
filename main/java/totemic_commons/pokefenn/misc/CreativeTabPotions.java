package totemic_commons.pokefenn.misc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import totemic_commons.pokefenn.ModItems;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CreativeTabPotions extends CreativeTabs
{

    public CreativeTabPotions(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

    @Override
    public Item getTabIconItem()
    {
        return ModItems.potion;
    }
}
