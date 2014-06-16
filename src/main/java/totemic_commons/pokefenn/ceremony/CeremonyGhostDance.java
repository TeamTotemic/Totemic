package totemic_commons.pokefenn.ceremony;

import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.blessing.BlessingHandler;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyGhostDance extends CeremonyBase
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        BlessingHandler.increaseBlessing(1, tileEntity.getWorldObj().getClosestPlayer(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, 8).getDisplayName());
    }
}
