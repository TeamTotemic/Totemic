package totemic_commons.pokefenn;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.lib.Strings;

public class CreativeTabTotemic extends CreativeTabs
{
    public CreativeTabTotemic(int id, String name)
    {
        super(id, name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getTabIconItem()
    {
        return Item.getItemFromBlock(ModBlocks.tipi);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void displayAllReleventItems(List<ItemStack> list)
    {
        super.displayAllReleventItems(list);

        list.add(getEgg("totemic." + Strings.BUFFALO_NAME));
        list.add(getEgg("totemic." + Strings.BAYKOK_NAME));
    }

    public static ItemStack getEgg(String entityName)
    {
        ItemStack stack = new ItemStack(Items.spawn_egg);
        NBTTagCompound eggTag = new NBTTagCompound();
        eggTag.setString("entity_name", entityName);
        stack.setTagCompound(eggTag);
        return stack;
    }
}
