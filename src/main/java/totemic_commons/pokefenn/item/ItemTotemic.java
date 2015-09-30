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
    protected boolean registerIcons = true;
    protected boolean creativeTab = true;

    public ItemTotemic(String name)
    {
        super();
        setMaxStackSize(64);
        setNoRepair();
        if(!name.equals(""))
            setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
        if(creativeTab)
            setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        if(registerIcons && !hasSubtypes)
            itemIcon = iconRegister.registerIcon(getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
    }


}
