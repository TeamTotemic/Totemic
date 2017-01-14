package totemic_commons.pokefenn.tileentity.totem;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import totemic_commons.pokefenn.api.music.MusicAcceptor;
import totemic_commons.pokefenn.api.music.MusicInstrument;

abstract class TotemState implements ITickable, MusicAcceptor
{
    protected final TileTotemBase tile;

    static TotemState fromID(int id, TileTotemBase tile)
    {
        switch(id)
        {
        case StateTotemEffect.ID:
            return new StateTotemEffect(tile);
        case StateSelection.ID:
            return new StateSelection(tile);
        case StateStartup.ID:
            return new StateStartup(tile);
        case StateCeremonyEffect.ID:
            return new StateCeremonyEffect(tile);
        default:
            throw new IllegalArgumentException("Invalid Totem Base state");
        }
    }

    TotemState(TileTotemBase tile)
    {
        this.tile = tile;
    }

    boolean canSelect()
    {
        return false;
    }

    void addSelector(MusicInstrument instr) { }

    abstract int getID();

    abstract void writeToNBT(NBTTagCompound tag);

    abstract void readFromNBT(NBTTagCompound tag);
}
