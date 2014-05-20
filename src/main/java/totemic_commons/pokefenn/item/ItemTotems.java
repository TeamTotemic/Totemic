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
import totemic_commons.pokefenn.api.totem.ITotem;
import totemic_commons.pokefenn.lib.Strings;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/11/13
 * Time: 18:32
 */
public class ItemTotems extends ItemTotemic implements ITotem
{

    public static final String[] TOTEM_NAMES = new String[]{"Horse", "Bat", "Blaze", "Ocelot", "Squid", "Draining", "Spider", "Cow"};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public static int horse = 0;
    public static int bat = 1;
    public static int blaze = 2;
    public static int ocelot = 3;
    public static int squid = 4;
    public static int draining = 5;
    public static int spider = 6;
    public static int cow = 7;

    public ItemTotems()
    {
        super();
        this.setHasSubtypes(true);
        maxStackSize = 1;
        setCreativeTab(Totemic.tabsTotem);
        registerIcons = false;
        setCreativeTab(null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if(stack.getItemDamage() == horse)
            list.add("Obtain the speed of a Horse");
        if(stack.getItemDamage() == bat)
            list.add("Fly like a Bat");
        if(stack.getItemDamage() == blaze)
            list.add("Burn like a Blaze");
        if(stack.getItemDamage() == ocelot)
            list.add("Fear creepers like a Ocelot");
        if(stack.getItemDamage() == squid)
            list.add("Breath underwater like a Squid");
        if(stack.getItemDamage() == draining)
            list.add("Drain the essence of nearby plants");
        if(stack.getItemDamage() == spider)
            list.add("Climb walls with the agility of a Spider");
        if(stack.getItemDamage() == cow)
            list.add("Become as sturdy and slow as a Cow");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list)
    {
        for(int meta = 0; meta < TOTEM_NAMES.length; meta++)
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

}
