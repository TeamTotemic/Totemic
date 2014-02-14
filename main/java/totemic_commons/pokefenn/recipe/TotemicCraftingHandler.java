package totemic_commons.pokefenn.recipe;

import cpw.mods.fml.common.ICraftingHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.ModItems;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/02/14
 * Time: 20:33
 */
public class TotemicCraftingHandler implements ICraftingHandler
{
    @Override
    public void onCrafting(EntityPlayer player, ItemStack itemStack, IInventory craftMatrix)
    {
        if(itemStack.itemID == ModItems.bucketChlorophyll.itemID && !player.worldObj.isRemote)
        {
            player.inventory.addItemStackToInventory(new ItemStack(Item.bucketEmpty));
        }

    }

    @Override
    public void onSmelting(EntityPlayer player, ItemStack item)
    {

    }
}
