package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 28/01/14
 * Time: 12:35
 */
public class ItemTotemic extends Item
{
    public ItemTotemic(String name, boolean creativeTab)
    {
        setNoRepair();
        if(!name.equals(""))
            setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
        if(creativeTab)
            setCreativeTab(Totemic.tabsTotem);
    }

    public ItemTotemic(String name)
    {
        this(name, true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        if(!hasSubtypes)
            itemIcon = iconRegister.registerIcon(getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
    }


}
