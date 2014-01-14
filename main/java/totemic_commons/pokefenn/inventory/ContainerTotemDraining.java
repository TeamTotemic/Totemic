package totemic_commons.pokefenn.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import totemic_commons.pokefenn.tileentity.TileTotemDraining;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 14/01/14
 * Time: 11:30
 */
public class ContainerTotemDraining extends Container {

    private TileTotemDraining totemDraining;

    public ContainerTotemDraining(InventoryPlayer inventoryPlayer, TileTotemDraining tileTotemDraining)
    {

        this.addSlotToContainer(new Slot(tileTotemDraining, TileTotemDraining.SLOT_ONE, 79, 17));

    }


    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }
}
