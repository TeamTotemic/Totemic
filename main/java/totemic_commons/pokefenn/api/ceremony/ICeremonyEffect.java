package totemic_commons.pokefenn.api.ceremony;

import totemic_commons.pokefenn.tileentity.totem.TileCeremonyIntelligence;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public interface ICeremonyEffect
{
    /**
     * @param tileCeremonyIntelligence This is the tile entity of the Ceremony Intelligence, this is what you do all your crazy effects from!
     */
    public void effect(TileCeremonyIntelligence tileCeremonyIntelligence);
}
