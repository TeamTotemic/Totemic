package totemic_commons.pokefenn.misc;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.entity.ModEntities;

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

    @SideOnly(Side.CLIENT)
    @Override
    public void displayAllReleventItems(List list) {
        super.displayAllReleventItems(list);
        list.add(new ItemStack(Items.spawn_egg, 1, ModEntities.buffalo));
    }
}
