package totemic_commons.pokefenn.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.tileentity.TileTotemBase;

public class ContainerTotemBase extends Container {

    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;

    public ContainerTotemBase(InventoryPlayer inventoryPlayer, TileTotemBase tileTotemBase)
    {

        this.addSlotToContainer(new Slot(tileTotemBase, TileTotemBase.TOTEM_BASE_HEAD_INDEX, 79, 17));
        this.addSlotToContainer(new Slot(tileTotemBase, TileTotemBase.TOTEM_BASE_CRYSTAL_INDEX, 79, 53));

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
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex)
    {

        ItemStack itemStack = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack())
        {
            ItemStack slotItemStack = slot.getStack();
            itemStack = slotItemStack.copy();

            if (slotIndex < TileTotemBase.INVENTORY_SIZE)
            {

                if (!this.mergeItemStack(slotItemStack, 1, inventorySlots.size(), true))
                {
                    return null;
                }
            } else
            {
                if (!this.mergeItemStack(slotItemStack, 0, TileTotemBase.INVENTORY_SIZE, false))
                {
                    return null;
                }
            }

            if (slotItemStack.stackSize == 0)
            {
                slot.putStack((ItemStack) null);
            } else
            {
                slot.onSlotChanged();
            }
        }

        return itemStack;
    }


}
