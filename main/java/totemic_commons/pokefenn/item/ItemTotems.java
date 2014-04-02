package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
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
public class ItemTotems extends ItemTotemic
{

    public static final String[] TOTEM_NAMES = new String[]{"NotATotem", "Horse", "Bat", "Blaze", "Ocelot", "Squid", "Draining" /*, "Spider"*/};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemTotems()
    {
        super();
        this.setHasSubtypes(true);
        maxStackSize = 1;
        setCreativeTab(Totemic.tabsTotem);
        registerIcons = false;

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        switch (stack.getItemDamage())
        {
            case 1:
                list.add("Damage mobs with the power of a Cactus");
                break;

            case 2:
                list.add("Obtain the speed of a Horse");
                break;

            case 3:
                list.add("Collect nearby items");
                break;

            case 4:
                list.add("Fly like a Bat");
                break;

            case 5:
                list.add("Wish away the rain");
                break;

            case 6:
                list.add("Burn like a Blaze");
                break;

            case 7:
                list.add("Fear creepers like a Ocelot");
                break;

            case 8:
                list.add("Breath underwater like a Squid");
                break;

            case 9:
                list.add("Feed nearby players");
                break;

            case 10:
                list.add("Bring love to nearby animals");
                break;

            case 11:
                list.add("Drain the essence of nearby plants");
                break;

            case 12:
                list.add("Increase the range of Totems");
                break;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < TOTEM_NAMES.length; meta++)
            list.add(new ItemStack(id, 1, meta));
    }


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

        for (int i = 0; i < TOTEM_NAMES.length; ++i)
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

}
