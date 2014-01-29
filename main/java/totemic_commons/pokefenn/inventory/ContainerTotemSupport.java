package totemic_commons.pokefenn.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import totemic_commons.pokefenn.tileentity.TileTotemSupport;


/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 29/01/14
 * Time: 10:55
 */
public class ContainerTotemSupport extends Container
{

    private TileTotemSupport totemSupport;

    public ContainerTotemSupport(InventoryPlayer inventoryPlayer, TileTotemSupport tileTotemSupport)
    {

        this.addSlotToContainer(new Slot(tileTotemSupport, TileTotemSupport.SLOT_ONE, 79, 17));

    }


    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

}
