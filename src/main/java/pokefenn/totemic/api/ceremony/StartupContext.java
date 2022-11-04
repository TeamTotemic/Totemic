package pokefenn.totemic.api.ceremony;

import javax.annotation.Nullable;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import pokefenn.totemic.api.music.MusicInstrument;

/**
 * This interface provides details about a Ceremony's progress and state during the startup phase.
 * Instances of this are passed to {@link CeremonyInstance} methods.
 */
public interface StartupContext {
    /**
     * Returns the time in ticks how long the startup phase lasted so far.
     * <p>
     * Note that the time is not necessarily synchronized between the server and the client, especially in case of server lag.
     * On the client side, the returned value may be greater than the ceremony's {@linkplain Ceremony#getAdjustedMaxStartupTime maximum startup time}.
     */
    int getTime();

    /**
     * Returns the amount of music from all instruments played for the ceremony.
     */
    int getTotalMusic();

    /**
     * Returns the amount of music from the specified instrument played for the ceremony.
     */
    int getMusic(MusicInstrument instrument);

    /**
     * If the ceremony was initiated by a player, returns that player. Otherwise, returns {@code null}.
     */
    @Nullable Player getInitiatingPlayer();

    /**
     * Returns the Entity that initiated the ceremony, if available.
     * Returns {@code null} if the initiating entity is no longer available (e.g. when
     * the world has been saved and reloaded).
     */
    @Nullable Entity getInitiator();

    /**
     * Instantly aborts the ceremony and returns the Totem Base to its default state.
     */
    void failCeremony();

    /**
     * Instantly starts the ceremony effect, skipping ahead of the rest of the startup phase.
     */
    void startCeremony();
}
