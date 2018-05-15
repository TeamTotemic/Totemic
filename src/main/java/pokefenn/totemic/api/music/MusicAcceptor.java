package pokefenn.totemic.api.music;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import pokefenn.totemic.api.TotemicCapabilities;

/**
 * Implement this interface as a Capability ({@link TotemicCapabilities#MUSIC_ACCEPTOR}) for a tile entity that can accept music
 * like a Totem Base.
 *
 * <p>A reference implementation is provided by {@link DefaultMusicAcceptor}.
 */
public interface MusicAcceptor
{
    /**
     * Accepts music from the given instrument, possibly played by an entity.
     * @param instr the music instrument
     * @param amount the amount of music
     * @param x
     * @param y the instrument's location. This might be different from the entity's position (e.g. Drum).
     * @param z
     * @param entity the entity playing the instrument. Might be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @return {@code true} if this call had any effect on the acceptor.
     */
    boolean acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity);
}
