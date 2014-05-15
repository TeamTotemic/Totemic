package totemic_commons.pokefenn.ceremony;

import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.tileentity.totem.TileCeremonyIntelligence;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyCrafting implements ICeremonyEffect
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        TileCeremonyIntelligence tileCeremony = (TileCeremonyIntelligence) tileEntity;

        if(tileCeremony != null)
        {
            //TODO
        }
    }
}
