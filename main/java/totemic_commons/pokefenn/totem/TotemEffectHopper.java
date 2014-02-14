package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 01/02/14
 * Time: 21:07
 */
public class TotemEffectHopper
{

    public static void effect(TileTotemIntelligence totem, int i)
    {

        TileEntity tileEntity = totem.worldObj.getBlockTileEntity(totem.xCoord, totem.yCoord - 1, totem.zCoord);

        if (totem.worldObj.getWorldTime() % 80L == 0)
        {

            if (EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10, 10) != null)
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(totem.worldObj, totem.xCoord, totem.yCoord, totem.zCoord, 10, 10))
                {
                    if (entity instanceof EntityItem)
                    {
                        //((IInventory) tileEntity).setInventorySlotContents(1, ((EntityItem) entity).getEntityItem());
                        EntityItem entityItem = new EntityItem(totem.worldObj, totem.xCoord, totem.yCoord - 1, totem.zCoord, ((EntityItem) entity).getEntityItem());
                        totem.worldObj.spawnEntityInWorld(entityItem);
                        totem.worldObj.markBlockForUpdate(totem.xCoord, totem.yCoord - 1, totem.zCoord);

                        ((EntityItem) entity).setDead();

                        totem.decreaseChlorophyll(TotemUtil.decrementAmount(i));

                    }
                }
            }
        }
    }

}






