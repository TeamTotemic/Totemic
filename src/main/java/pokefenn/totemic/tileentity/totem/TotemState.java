package pokefenn.totemic.tileentity.totem;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import pokefenn.totemic.api.music.MusicAcceptor;
import pokefenn.totemic.api.music.MusicInstrument;

public abstract class TotemState implements ITickable, MusicAcceptor
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

    @Override
    public abstract boolean acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity);

    public boolean canSelect()
    {
        return false;
    }

    public void addSelector(@Nullable Entity entity, MusicInstrument instr) { }

    abstract int getID();

    abstract void writeToNBT(NBTTagCompound tag);

    abstract void readFromNBT(NBTTagCompound tag);

    void spawnParticles(EnumParticleTypes type, int number)
    {
        BlockPos pos = tile.getPos();
        ((WorldServer) tile.getWorld()).spawnParticle(type, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, number, 0.5, 0.5, 0.5, 0.0);
    }
}
