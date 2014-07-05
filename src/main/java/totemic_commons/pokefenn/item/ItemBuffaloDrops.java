package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemBuffaloDrops extends ItemTotemic
{

    private static final String[] BUFFALO_ITEM_NAMES = new String[]{"Teeth", "Hide", "Horns", "Hair", "Hoove", "Dung"};

    public static final int teeth = 0;
    public static final int hide = 1;
    public static final int horn = 2;
    public static final int hair = 3;
    public static final int hoove = 4;
    public static final int dung = 5;

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemBuffaloDrops()
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
        int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, BUFFALO_ITEM_NAMES.length - 1);

        unlocalizedName.append("item.");
        unlocalizedName.append(Strings.RESOURCE_PREFIX);
        unlocalizedName.append("buffalo");
        unlocalizedName.append(BUFFALO_ITEM_NAMES[meta]);

        return unlocalizedName.toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        int j = MathHelper.clamp_int(meta, 0, BUFFALO_ITEM_NAMES.length - 1);
        return icons[j];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[BUFFALO_ITEM_NAMES.length];

        for(int i = 0; i < BUFFALO_ITEM_NAMES.length; ++i)
            icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + "buffalo" + BUFFALO_ITEM_NAMES[i]);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list)
    {
        for(int meta = 0; meta < BUFFALO_ITEM_NAMES.length; ++meta)
            list.add(new ItemStack(id, 1, meta));
    }
}
