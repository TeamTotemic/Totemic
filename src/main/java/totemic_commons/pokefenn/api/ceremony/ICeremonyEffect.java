package totemic_commons.pokefenn.api.ceremony;

import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.tileentity.totem.TileCeremonyIntelligence;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public interface ICeremonyEffect
{
    /**
     * @param tileEntity This is the tile entity of the Ceremony Intelligence, this is what you do all your crazy effects from!
     *                   Remember to cast it into TileTotemBase if you are going to interact with the tile entity ;)
     */
    public void effect(TileEntity tileEntity);
}
