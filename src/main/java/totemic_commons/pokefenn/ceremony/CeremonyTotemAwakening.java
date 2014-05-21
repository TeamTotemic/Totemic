package totemic_commons.pokefenn.ceremony;

import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.tileentity.totem.TileCeremonyIntelligence;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyTotemAwakening implements ICeremonyEffect
{
    @Override
    public void effect(TileEntity tileEntity)
    {
        TileTotemBase tileTotemBase = (TileTotemBase) tileEntity;

        int music = tileTotemBase.musicFromCeremony;

        if(music > 100)
        {
            tileTotemBase.tier = 1;
        }
        if(music > 150)
        {
            tileTotemBase.tier = 2;
        }
        if(music > 200)
        {
            tileTotemBase.tier = 3;
        }
    }
}
