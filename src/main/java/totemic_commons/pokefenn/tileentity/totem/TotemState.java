package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.util.ITickable;
import totemic_commons.pokefenn.api.music.MusicAcceptor;

public abstract class TotemState implements ITickable, MusicAcceptor
{
    protected final TileTotemBase tile;

    public TotemState(TileTotemBase tile)
    {
        this.tile = tile;
    }
}
