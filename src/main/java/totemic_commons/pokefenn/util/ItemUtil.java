package totemic_commons.pokefenn.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemUtil
{
    /**
     * @return the stack's NBTTagCompound, assigning a new one if it has none yet
     */
    public static NBTTagCompound getOrCreateTag(ItemStack stack)
    {
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null)
        {
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }
        return tag;
    }
}
