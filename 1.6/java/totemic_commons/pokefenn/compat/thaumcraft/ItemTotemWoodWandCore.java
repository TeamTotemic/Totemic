/*package totemic_commons.pokefenn.compat.thaumcraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ItemApi;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

import java.util.List;

public class ItemTotemWoodWandCore extends Item
{

    public ItemTotemWoodWandCore(int id)
    {
        super(id);
        setUnlocalizedName(Strings.TOTEM_WOOD_WAND_CORE_NAME);
        setMaxStackSize(1);
        setCreativeTab(Totemic.tabsTotem);
        setMaxDamage(0);


    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(int par1, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(this, 1));

        ItemStack totemWood = ItemApi.getItem("itemWandCasting", 72);
        list.add(totemWood);

    }




}

*/