package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.verdant.IVerdantCrystal;
import totemic_commons.pokefenn.lib.Strings;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/02/14
 * Time: 14:53
 */
public class ItemBlazingVerdantCrystal extends ItemTotemic implements IVerdantCrystal
{
    public ItemBlazingVerdantCrystal()
    {
        super();
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.BLAZING_VERDANT_CRYSTAL_NAME);
        setMaxDamage(ModItems.verdantCrystal.getMaxDamage());
        setMaxStackSize(1);
        //registerIcons = false;
    }


    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack copiedStack = itemStack.copy();
        copiedStack.stackSize = 1;
        copiedStack.setItemDamage(itemStack.getItemDamage() + 1);
        return copiedStack;

    }

    @Override
    public boolean hasContainerItem()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.rare;
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("A Blazing crystal holding plant essence");
        list.add("Maybe I could use this in a furnace...");
    }

    public void getSubItems(Item id, CreativeTabs par2CreativeTabs, List list)
    {
        list.add(new ItemStack(id, 1, 1000));
        list.add(new ItemStack(id, 1, 500));
        list.add(new ItemStack(id, 1, 1));
    }


}
