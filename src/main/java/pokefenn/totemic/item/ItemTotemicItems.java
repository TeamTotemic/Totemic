package pokefenn.totemic.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.lib.Strings;

public class ItemTotemicItems extends ItemTotemic
{
    public enum Type
    {
        eagle_bone, iron_bells;
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
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        if(isInCreativeTab(tab))
        {
            for(int meta = 0; meta < Type.values().length; meta++)
                list.add(new ItemStack(this, 1, meta));
        }
    }
}
