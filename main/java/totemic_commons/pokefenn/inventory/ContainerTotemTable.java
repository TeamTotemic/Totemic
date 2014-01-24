package totemic_commons.pokefenn.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import totemic_commons.pokefenn.tileentity.TileTotemTable;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 14/01/14
 * Time: 21:41
 */
public class ContainerTotemTable extends Container
{

    private TileTotemTable totemTable;

    public ContainerTotemTable(InventoryPlayer inventoryPlayer, TileTotemTable tileTotemTable)
    {

        this.addSlotToContainer(new Slot(tileTotemTable, TileTotemTable.SLOT_ONE, 79, 17));

    }


    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

}