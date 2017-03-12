package totemic_commons.pokefenn.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

public class ItemTotemicItems extends ItemTotemic
{
    public enum Type
    {
        iron_nugget, iron_bells;
    }

    public ItemTotemicItems()
    {
        super("");
        setRegistryName(Strings.SUB_ITEMS_NAME);
        setHasSubtypes(true);
        setMaxStackSize(64);
        setCreativeTab(Totemic.tabsTotem);
    }


    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int index = MathHelper.clamp(itemStack.getItemDamage(), 0, Type.values().length - 1);
        return "item." + Strings.RESOURCE_PREFIX + Type.values()[index].toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, NonNullList<ItemStack> list)
    {
        for(int meta = 0; meta < Type.values().length; ++meta)
            list.add(new ItemStack(id, 1, meta));
    }
}
