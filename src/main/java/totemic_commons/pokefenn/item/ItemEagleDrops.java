package totemic_commons.pokefenn.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import totemic_commons.pokefenn.lib.Strings;

public class ItemEagleDrops extends ItemTotemic
{
    public enum Type
    {
        bone, feather;

        public final String name;

        private Type()
        {
            this.name = "eagle" + Character.toUpperCase(name().charAt(0)) + name().substring(1);
        }
    }

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemEagleDrops()
    {
        super("");
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        Type type = Type.values()[MathHelper.clamp_int(itemStack.getItemDamage(), 0, Type.values().length - 1)];
        return "item." + Strings.RESOURCE_PREFIX + type.name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        int j = MathHelper.clamp_int(meta, 0, Type.values().length - 1);
        return icons[j];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        icons = new IIcon[Type.values().length];

        for(int i = 0; i < Type.values().length; ++i)
            icons[i] = iconRegister.registerIcon(Strings.RESOURCE_PREFIX + Type.values()[i].name);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List<ItemStack> list)
    {
        for(int meta = 0; meta < Type.values().length; ++meta)
            list.add(new ItemStack(id, 1, meta));
    }
}
