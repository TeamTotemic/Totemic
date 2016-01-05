package totemic_commons.pokefenn.misc;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.ModBlocks;

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
        return Item.getItemFromBlock(ModBlocks.tipi);
    }

    /*@SideOnly(Side.CLIENT)
    @Override
    public void displayAllReleventItems(List list) {
        super.displayAllReleventItems(list);
    }*/
}
