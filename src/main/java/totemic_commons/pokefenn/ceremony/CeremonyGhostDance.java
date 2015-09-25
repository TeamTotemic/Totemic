package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.blessing.BlessingHandler;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyGhostDance extends CeremonyBase
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        for(Entity entity : EntityUtil.getEntitiesInRange(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, 8, 8))
        if(entity instanceof EntityPlayer)
        {
            BlessingHandler.increaseBlessing(2, ((EntityPlayer) entity).getDisplayName(), tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        }

    }
}
