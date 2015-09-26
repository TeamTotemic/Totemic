package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyBuffaloDance extends CeremonyBase
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        if(tileEntity != null)
        {
            int i = 0;

            for(Entity entity : EntityUtil.getEntitiesInRange(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, 8, 8))
            {
                if(i < 2)
                {
                    if(entity instanceof EntityCow && !(entity instanceof EntityBuffalo))
                    {
                        i++;
                        EntityUtil.spawnEntity(tileEntity.getWorldObj(), entity.posX, entity.posY, entity.posZ, new EntityBuffalo(tileEntity.getWorldObj()));
                        entity.setDead();
                    }
                }
            }

        }
    }
}
