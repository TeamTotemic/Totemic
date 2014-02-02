package totemic_commons.pokefenn.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 01/02/14
 * Time: 21:07
 */
public class TotemEffectHopper
{

    public static void effect(TileTotemBase tileTotemBase)
    {

        Block blockUnder = Block.blocksList[tileTotemBase.worldObj.getBlockId(tileTotemBase.xCoord, tileTotemBase.yCoord - 1, tileTotemBase.zCoord)];

        if (tileTotemBase.getStackInSlot(tileTotemBase.SLOT_TWO) != null)
        {

            if (tileTotemBase.getStackInSlot(tileTotemBase.SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
            {

                if (EntityUtil.getEntitiesInRange(tileTotemBase.worldObj, tileTotemBase.xCoord, tileTotemBase.yCoord, tileTotemBase.zCoord, 10, 10) != null && !(tileTotemBase.getStackInSlot(tileTotemBase.SLOT_TWO).getMaxDamage() - tileTotemBase.getStackInSlot(tileTotemBase.SLOT_TWO).getItemDamage() - tileTotemBase.DECREASE_HOPPER <= 0))
                {

                    for (Entity entity : EntityUtil.getEntitiesInRange(tileTotemBase.worldObj, tileTotemBase.xCoord, tileTotemBase.yCoord, tileTotemBase.zCoord, 10, 10))
                    {
                        if (entity instanceof EntityItem)
                        {

                            if (blockUnder instanceof IInventory)
                            {
                                //((IInventory) blockUnder).setInventorySlotContents((1 , ((EntityItem) entity).getEntityItem());
                                entity.setDead();
                                tileTotemBase.chlorophyllCrystalHandler(tileTotemBase.DECREASE_HOPPER);

                            }
                        }
                    }
                }
            }
        }
    }

}
