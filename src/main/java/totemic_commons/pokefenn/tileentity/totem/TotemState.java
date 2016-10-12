package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.util.ITickable;
import totemic_commons.pokefenn.api.music.MusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicInstrument;

public abstract class TotemState implements ITickable, MusicAcceptor
{
    protected final TileTotemBase tile;

    public TotemState(TileTotemBase tile)
    {
        this.tile = tile;
    }

    public boolean canSelect()
    {
        return false;
    }

    public void addSelector(MusicInstrument instr) { }
}
