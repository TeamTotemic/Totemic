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
    public boolean registerIcons = true;
    public boolean creativeTab = true;

    public ItemTotemic(String name)
    {
        super();
        setMaxStackSize(64);
        setNoRepair();
        unlocalisedNameStuffs(name);
        if(!name.equals(""))
            setUnlocalizedName(Strings.RESOURCE_PREFIX + name);
        if(creativeTab)
            setCreativeTab(Totemic.tabsTotem);
    }

    public void unlocalisedNameStuffs(String name)
    {
        //String substringed = name.substring(5);

        /*
        if(substringed.contains("totem") || substringed.contains("totemic"))
            if(substringed.contains("totem"))
                GameRegistry.registerItem(this, name.substring(5));
            else
                GameRegistry.registerItem(this, name.substring(8));
        else
            GameRegistry.registerItem(this, name);
            */
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        if(registerIcons && !hasSubtypes)
            itemIcon = iconRegister.registerIcon(getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
    }


}
