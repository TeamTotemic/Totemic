package totemic_commons.pokefenn.item.verdant;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.api.verdant.IVerdantCrystal;
import totemic_commons.pokefenn.item.ItemTotemic;
import totemic_commons.pokefenn.lib.Strings;

import java.util.List;

public class ItemVerdantCrystal extends ItemTotemic implements IVerdantCrystal
{


    public ItemVerdantCrystal()
    {
        super();
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.VERDANT_CRYSTAL_NAME);
        setMaxStackSize(1);
        setMaxDamage(1000);
        registerIcons = false;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("A crystal holding plant essence");
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 499));
        list.add(new ItemStack(item, 1, 1000));
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.rare;
    }

}


