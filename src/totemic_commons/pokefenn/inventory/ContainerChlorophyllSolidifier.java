package totemic_commons.pokefenn.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import totemic_commons.pokefenn.tileentity.TileChlorophyllSolidifier;

/**
 * Created with IntelliJ IDEA.
 * User: ${Pokefenn}
 * Date: 13/11/13
 * Time: 19:23
 */
public class ContainerChlorophyllSolidifier extends Container {



    private TileChlorophyllSolidifier chlorophyllSolidifier;

    public ContainerChlorophyllSolidifier(InventoryPlayer inventoryPlayer, TileChlorophyllSolidifier tileChlorophyllSolidifier){


        this.addSlotToContainer(new Slot(tileChlorophyllSolidifier, TileChlorophyllSolidifier.INVENTORY_SLOT_INDEX, 79, 17));


    }


    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }
}
