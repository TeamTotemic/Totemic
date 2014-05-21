package totemic_commons.pokefenn.ceremony;

import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyCrafting implements ICeremonyEffect
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        TileTotemBase tileCeremony = (TileTotemBase) tileEntity;

        if(tileCeremony != null)
        {
            //TODO
        }
    }
}
