package totemic_commons.pokefenn;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.lib.Strings;

public class CreativeTabTotemic extends CreativeTabs
{
    public CreativeTabTotemic(String name)
    {
        super(name);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(ModBlocks.tipi);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void displayAllRelevantItems(NonNullList<ItemStack> list)
    {
        super.displayAllRelevantItems(list);

        list.add(getEgg(Totemic.MOD_ID + ":" + Strings.BUFFALO_NAME));
        list.add(getEgg(Totemic.MOD_ID + ":" + Strings.BAYKOK_NAME));
    }

    public static ItemStack getEgg(String entityName)
    {
        ItemStack stack = new ItemStack(Items.SPAWN_EGG);
        NBTTagCompound entityTag = new NBTTagCompound();
        entityTag.setString("id", entityName);
        NBTTagCompound eggTag = new NBTTagCompound();
        eggTag.setTag("EntityTag", entityTag);
        stack.setTagCompound(eggTag);
        return stack;
    }
}
