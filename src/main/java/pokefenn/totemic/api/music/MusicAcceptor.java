package pokefenn.totemic.api.music;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import pokefenn.totemic.api.TotemicCapabilities;

/**
 * Implement this interface as a Capability ({@link TotemicCapabilities#MUSIC_ACCEPTOR}) for a tile entity that can accept music like a Totem Base.
 *
 * <p>
 * A reference implementation is provided by {@link DefaultMusicAcceptor}.
 */
@AutoRegisterCapability
public interface MusicAcceptor {
    /**
     * Default priority for MusicAcceptors.
     */
    static final int DEFAULT_PRIORITY = 0;
    /**
     * Music acceptor priority of a Totem Base currently performing a Ceremony.
     */
    static final int CEREMONY_PRIORITY = 16;

    /**
     * Returns {@code true} if the acceptor is able to accept music from the specified instrument. If the acceptor is saturated with the instrument,
     * {@code false} should be returned.
     * @param instr the music instrument
     */
    boolean canAcceptMusic(MusicInstrument instr);

    /**
     * Accepts music from the given instrument, possibly played by an entity.
     *
     * @param instr  the music instrument
     * @param amount the amount of music
     * @param x
     * @param y      the instrument's location. This might be different from the entity's position (e.g. Drum).
     * @param z
     * @param entity the entity playing the instrument. Might be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @return {@code true} if this call had any effect on the acceptor.
     */
    boolean acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity);

    /**
     * Returns the position of this acceptor.
     */
    Vec3 getPosition();

    /**
     * Returns the priority of the Music Acceptor. Higher priority acceptors will receive music before other acceptors. The music will be evenly split between
     * acceptors with the same priority.
     */
    default int getPriority() {
        return DEFAULT_PRIORITY;
    }
}
