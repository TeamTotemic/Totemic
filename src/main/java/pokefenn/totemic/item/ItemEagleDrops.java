package pokefenn.totemic.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import pokefenn.totemic.lib.Strings;

public class ItemEagleDrops extends ItemTotemic
{
    public enum Type
    {
        bone, feather;

        private final String fullName = "eagle_" + name();

        @Override
        public String toString()
        {
            return fullName;
        }
    }

    public ItemEagleDrops()
    {
        super("");
        setRegistryName(Strings.EAGLE_DROPS_NAME);
        setHasSubtypes(true);
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
            for(int meta = 0; meta < Type.values().length; ++meta)
                list.add(new ItemStack(this, 1, meta));
        }
    }
}
