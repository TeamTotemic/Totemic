package totemic_commons.pokefenn.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.lib.Strings;

public class ItemBuffaloDrops extends ItemTotemic
{
    public enum Type
    {
        hide, teeth/*, horn, hair, hoof, dung*/;

        private final String fullName = "buffalo_" + name();

        @Override
        public String toString()
        {
            return fullName;
        }
    }

    public ItemBuffaloDrops()
    {
        super("");
        setRegistryName(Strings.BUFFALO_ITEMS_NAME);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int index = MathHelper.clamp(itemStack.getItemDamage(), 0, Type.values().length - 1);
        return "item." + Strings.RESOURCE_PREFIX + Type.values()[index].toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> list)
    {
        for(int meta = 0; meta < Type.values().length; ++meta)
            list.add(new ItemStack(this, 1, meta));
    }
}
