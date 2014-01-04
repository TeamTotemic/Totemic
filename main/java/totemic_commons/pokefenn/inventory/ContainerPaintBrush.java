package totemic_commons.pokefenn.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.ItemStackNBTHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 04/12/13
 * Time: 18:29
 */
public class ContainerPaintBrush extends Container {


    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;

    public ContainerPaintBrush(InventoryPlayer inventoryPlayer)
    {

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex)
        {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 84 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; ++actionBarSlotIndex)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 142));
        }


    }


    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {

        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {

        super.onContainerClosed(player);

        if (!player.worldObj.isRemote)
        {
            InventoryPlayer invPlayer = player.inventory;
            for (ItemStack itemStack : invPlayer.mainInventory)
            {
                if (itemStack != null)
                {
                    if (ItemStackNBTHelper.hasTag(itemStack, Strings.NBT_ITEM_PAINT_BRUSH_GUI_OPEN))
                    {
                        ItemStackNBTHelper.removeTag(itemStack, Strings.NBT_ITEM_PAINT_BRUSH_GUI_OPEN);
                    }
                }
            }
        }
    }
}
