package totemic_commons.pokefenn.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.lib.Strings;

import java.util.List;

public class ItemChlorophyllCrystal extends ItemNormal
{


    public ItemChlorophyllCrystal(int id)
    {
        super(id);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.CHLOROPHYLL_CRYSTAL_NAME);
        setMaxStackSize(1);
        setMaxDamage(1000);

    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add("A crystal holding plant essence");
    }

    public void getSubItems(int id, CreativeTabs par2CreativeTabs, List list)
    {
        list.add(new ItemStack(id, 1, 1000));
        list.add(new ItemStack(id, 1, 500));
        list.add(new ItemStack(id, 1, 1));
    }

}


