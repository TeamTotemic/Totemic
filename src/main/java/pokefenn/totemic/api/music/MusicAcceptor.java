package pokefenn.totemic.api.music;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

/**
 * An interface for tile entities that accept music.
 *
 * TODO: Make usable as Capability
 */
public interface MusicAcceptor
{
    /**
     * Accepts music from the given instrument located at the given position (which might differ from the entity's position),
     * played by the given entity if applicable.
     *
     * <p><b>The default implementation is only provided for backwards compatibility and will be removed in a future release!
     * Please override this method instead of {@link #addMusic}. Keep in mind that particles are not being spawned by default anymore,
     * so you will have to add them to your implementation of this method.</b>
     *
     * @implNote The default implementation calls {@link #addMusic} and spawns note or cloud particles at the tile entity's location
     * (we assume that the default implementation will only be used when MusicAcceptor is implemented directly by a TileEntity).
     *
     * @param instr the music instrument
     * @param amount the amount of music
     * @param x
     * @param y the instrument's location. This might be different from the entity's position (e.g. Drum).
     * @param z
     * @param entity the entity playing the instrument. Might be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @return {@code true} if this call had any effect on the acceptor.
     */
    default boolean acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity)
    {
        boolean result = addMusic(instr, amount);
        TileEntity tile = (TileEntity) this; //Backwards compatibility only, we can assume we won't have a capability.
        BlockPos pos = tile.getPos();
        ((WorldServer) tile.getWorld()).spawnParticle(result ? EnumParticleTypes.NOTE : EnumParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 6, 0.5, 0.5, 0.5, 0.0);
        return result;
    }

    /**
     * Adds up to the given amount music of the given instrument to the acceptor
     * @return true if any music was added
     * @deprecated Override {@link #acceptMusic} instead. Keep in mind that particles are not being
     * spawned by default anymore, so you will have to add them to your acceptMusic implementation.
     */
    @Deprecated
    default boolean addMusic(MusicInstrument instr, int amount)
    {
        throw new UnsupportedOperationException();
    }
}
