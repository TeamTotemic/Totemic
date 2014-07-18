package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/11/13
 * Time: 18:32
 */
public class ItemTotems extends Item
{

    //public static final String[] TOTEM_NAMES = new String[]{"Horse", "Bat", "Blaze", "Ocelot", "Squid", "Spider", "Cow"};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public static final int horse = 0;
    public static final int bat = 1;
    public static final int blaze = 2;
    public static final int ocelot = 3;
    public static final int squid = 4;
    public static final int spider = 5;
    public static final int cow = 6;

    public ItemTotems()
    {
        super();
        setHasSubtypes(true);
        setMaxStackSize(1);
    }


    //@Override
    //@SideOnly(Side.CLIENT)
    //public void getSubItems(Item id, CreativeTabs creativeTab, List list)
    //{
    //    for(int meta = 0; meta < TOTEM_NAMES.length; meta++)
    //        list.add(new ItemStack(id, 1, meta));
    //}
    /*


    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        StringBuilder unlocalizedName = new StringBuilder();
        int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, TOTEM_NAMES.length - 1);

        unlocalizedName.append("item.");
        unlocalizedName.append(Strings.RESOURCE_PREFIX);
        unlocalizedName.append(Strings.TOTEMS_NAME);
        unlocalizedName.append(TOTEM_NAMES[meta]);

        return unlocalizedName.toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[TOTEM_NAMES.length];

        for(int i = 0; i < TOTEM_NAMES.length; ++i)
            icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Strings.TOTEMS_NAME + TOTEM_NAMES[i]);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        int j = MathHelper.clamp_int(meta, 0, TOTEM_NAMES.length - 1);
        return icons[j];
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.uncommon;
    }
    */

}
