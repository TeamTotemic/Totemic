package pokefenn.totemic.tileentity.totem;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;

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

    void addSelector(@Nullable Entity entity, MusicInstrument instr) { }

    abstract int getID();

    abstract void writeToNBT(NBTTagCompound tag);

    abstract void readFromNBT(NBTTagCompound tag);
}
