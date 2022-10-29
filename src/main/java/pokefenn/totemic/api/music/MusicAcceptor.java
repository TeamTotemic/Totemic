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
     * Accepts music from the given instrument, possibly played by an entity.
     *
     * @param instr  the music instrument
     * @param amount the amount of music
     * @param x
     * @param y      the instrument's location. This might be different from the entity's position (e.g. Drum).
     * @param z
     * @param entity the entity playing the instrument. Might be {@code null} if the instrument is not driven by an entity (e.g. Wind Chime).
     * @return a {@link MusicResult} describing the result of this call.
     */
    MusicResult acceptMusic(MusicInstrument instr, int amount, double x, double y, double z, @Nullable Entity entity);

    /**
     * Returns the position of this acceptor.
     *
     * @see Vec3#atCenterOf(net.minecraft.core.Vec3i)
     */
    Vec3 getPosition();

    /**
     * Returns {@code true} if the acceptor is <em>generally</em> able to accept music from the specified instrument.
     * Note that this method should still return {@code true} if the acceptor is merely saturated with the instrument.
     * @param instr the music instrument
     */
    default boolean canAcceptMusic(MusicInstrument instr) {
        return true;
    }

    /**
     * Returns the priority of the Music Acceptor. Higher priority acceptors will receive music before other acceptors. The music will be evenly split between
     * acceptors with the same priority.
     */
    default int getPriority() {
        return DEFAULT_PRIORITY;
    }

    /**
     * Status value returned by {@link MusicAcceptor#acceptMusic}.
     */
    enum MusicResult {
        /** Indicates that all of the music was transferred into the acceptor. */
        SUCCESS,

        /** Indicates that some but not all of the music was transferred into the acceptor, leaving it saturated with the instrument. */
        SUCCESS_SATURATED,

        /** Indicates that no music was transferred into the acceptor because it was already saturated with the instrument. */
        SATURATED,

        /**
         * Indicates that no music could be transferred into the acceptor for reasons unrelated to saturation.
         * In this case, {@link MusicAcceptor#canAcceptMusic} should return {@code false} if possible.
         */
        FAILURE;

        /**
         * @return {@code true} if the result indicates that some music was transferred.
         */
        public boolean isSuccess() {
            return this == SUCCESS || this == SUCCESS_SATURATED;
        }

        /**
         * @return {@code true} if the result indicates that the acceptor is left saturated with the instrument.
         */
        public boolean isSaturated() {
            return this == SATURATED || this == SUCCESS_SATURATED;
        }
    }
}
