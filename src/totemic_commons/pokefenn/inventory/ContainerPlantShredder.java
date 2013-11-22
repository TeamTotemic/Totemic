package totemic_commons.pokefenn.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import totemic_commons.pokefenn.tileentity.TilePlantShredder;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 17/11/13
 * Time: 20:08
 */
public class ContainerPlantShredder extends Container {

    private TilePlantShredder plantShredder;

    public ContainerPlantShredder(InventoryPlayer inventoryPlayer, TilePlantShredder tilePlantShredder){


        this.addSlotToContainer(new Slot(tilePlantShredder, TilePlantShredder.INVENTORY_SLOT_INDEX, 79, 17));


    }



    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }
}
