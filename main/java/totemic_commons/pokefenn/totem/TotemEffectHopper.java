package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 01/02/14
 * Time: 21:07
 */
public class TotemEffectHopper
{

    public static void effect(TileTotemic totem, int i, int upgrades)
    {
        if (totem.worldObj.getWorldTime() % 80L == 0)
        {

            if (EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)) != null)
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)))
                {
                    if (entity instanceof EntityItem)
                    {
                        //((IInventory) tileEntity).setInventorySlotContents(1, ((EntityItem) entity).getEntityItem());
                        EntityItem entityItem = new EntityItem(totem.worldObj, totem.xCoord, totem.yCoord - 1, totem.zCoord, ((EntityItem) entity).getEntityItem());
                        totem.worldObj.spawnEntityInWorld(entityItem);
                        totem.worldObj.markBlockForUpdate(totem.xCoord, totem.yCoord - 1, totem.zCoord);

                        ((EntityItem) entity).setDead();

                    }
                }
            }
        }
    }

}






