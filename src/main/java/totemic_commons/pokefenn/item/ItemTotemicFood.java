package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

public class ItemTotemicFood extends ItemFood
{
    public ItemTotemicFood(String name, int amount, float saturation, boolean feedsWolf)
    {
        super(amount, saturation, feedsWolf);
        if(!name.equals(""))
            setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        if(!hasSubtypes)
            itemIcon = iconRegister.registerIcon(getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
    }
}
