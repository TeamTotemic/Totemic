package pokefenn.totemic.api.ceremony;

import pokefenn.totemic.api.music.MusicInstrument;

/**
 * This interface provides details about a Ceremony's progress and state during the startup phase.
 * Instances of this are passed to {@link Ceremony} methods.
 */
public interface StartupContext
{
    /**
     * @return the time in ticks how long the startup phase lasted so far.<br>
     * Note: This value might not be accurate on the client side due to server latency.
     */
    int getTime();

    /**
     * @return the amount of music from all instruments played for the ceremony.
     */
    int getTotalMusic();

    /**
     * @return the amount of music from the specified instrument played for the ceremony.
     */
    int getMusic(MusicInstrument instrument);
}
