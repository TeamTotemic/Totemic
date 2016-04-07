package totemic_commons.pokefenn.compat.waila;

import totemic_commons.pokefenn.block.totem.BlockTotemBase;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TotemicWailaMain
{
    public static void initiateWaila(IWailaRegistrar reg)
    {
        reg.registerBodyProvider(new WailaTotemBase(), BlockTotemBase.class);
    }
}
