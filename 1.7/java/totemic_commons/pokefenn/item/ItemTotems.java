package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/11/13
 * Time: 18:32
 */
public class ItemTotems extends ItemNormal
{

    public static final String[] TOTEM_NAMES = new String[]{"NotATotem", "Cactus", "Horse", "Hopper", "Bat", "Sun", "Blaze", "Ocelot", "Squid", "Food", "Love", "Draining", "Range"/*, "Spider"*/};

    //@SideOnly(Side.CLIENT)
    //private IIcon[] icons;

    public ItemTotems()
    {
        super();
        setHasSubtypes(true);
        maxStackSize = 1;
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int l = MathHelper.clamp_int(itemStack.getItemDamage(), 0, TOTEM_NAMES.length);
        return "item." + Strings.RESOURCE_PREFIX + Strings.TOTEMS_NAME + TOTEM_NAMES[l];
    }
    /*

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        //int j = MathHelper.clamp_int(meta, 0, TOTEM_NAMES.length - 1);
        //return icons[meta];
    }
    */
    /*

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        icons = new Icon[TOTEM_NAMES.length];

        for (int i = 0; i < TOTEM_NAMES.length; ++i)
        {
            icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.TOTEMS_NAME + TOTEM_NAMES[i]);
        }
    }
    */



    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < TOTEM_NAMES.length; ++meta)
        {
            list.add(new ItemStack(id, 1, meta));
        }
    }


    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.uncommon;
    }



}
