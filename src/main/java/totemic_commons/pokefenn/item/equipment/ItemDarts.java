package totemic_commons.pokefenn.item.equipment;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;

import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemDarts extends ItemTotemic
{
    public static String[] DART_NAMES = {"basicDart", "poisonDart", "blazeDart", "antidoteDart"/*, "blindingDart", "slowingDart"*/};
    @SideOnly(Side.CLIENT)
    public static IIcon[] icons;

    public static int basicDart = 0;
    public static int poisonDart = 1;
    public static int blazeDart = 2;
    public static int antidoteDart = 3;
    public static int blindingDart = 4;
    public static int slowingDart = 5;

    public ItemDarts()
    {
        super("");
        setMaxStackSize(16);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List list)
    {
        for(int meta = 0; meta < DART_NAMES.length; meta++)
            list.add(new ItemStack(id, 1, meta));
    }


    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        StringBuilder unlocalizedName = new StringBuilder();
        int meta = MathHelper.clamp_int(itemStack.getItemDamage(), 0, DART_NAMES.length - 1);

        unlocalizedName.append("item.");
        unlocalizedName.append(Strings.RESOURCE_PREFIX);
        unlocalizedName.append(DART_NAMES[meta]);

        return unlocalizedName.toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[DART_NAMES.length];

        for(int i = 0; i < DART_NAMES.length; ++i)
            icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + DART_NAMES[i]);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        int j = MathHelper.clamp_int(meta, 0, DART_NAMES.length - 1);
        return icons[j];
    }

}
