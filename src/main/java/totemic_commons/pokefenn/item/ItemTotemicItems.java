package totemic_commons.pokefenn.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/12/13
 * Time: 19:19
 */
public class ItemTotemicItems extends ItemTotemic
{

    private static final String[] ITEMS_NAMES = {"nuggetIron", "bellsIron"};

    public static final int nuggetIron = 0;
    public static final int bellsIron = 1;

    public ItemTotemicItems()
    {
        super("");
        setHasSubtypes(true);
        setMaxStackSize(64);
        setCreativeTab(Totemic.tabsTotem);
    }


    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {

        StringBuilder unlocalizedName = new StringBuilder();
        int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, ITEMS_NAMES.length - 1);

        unlocalizedName.append("item.");
        unlocalizedName.append(Strings.RESOURCE_PREFIX);
        unlocalizedName.append(ITEMS_NAMES[meta]);

        return unlocalizedName.toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List<ItemStack> list)
    {
        for(int meta = 0; meta < ITEMS_NAMES.length; ++meta)
            list.add(new ItemStack(id, 1, meta));
    }
}
