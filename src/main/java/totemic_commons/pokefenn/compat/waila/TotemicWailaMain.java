package totemic_commons.pokefenn.compat.waila;

import mcp.mobius.waila.api.IWailaRegistrar;
import totemic_commons.pokefenn.block.totem.BlockTotemBase;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TotemicWailaMain
{
    public void initiateWaila(IWailaRegistrar registrar)
    {
        registrar.registerBodyProvider(new WailaTotemBase(), BlockTotemBase.class);
    }
}
