package totemic_commons.pokefenn.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import totemic_commons.pokefenn.ModItems;

public class CreativeTabTotemic extends CreativeTabs
{
    public CreativeTabTotemic(int id, String name)
    {
        super(id, name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getTabIconItem()
    {
        return ModItems.tipi;
    }

    /*@SideOnly(Side.CLIENT)
    @Override
    public void displayAllReleventItems(List list) {
        super.displayAllReleventItems(list);
    }*/
}
