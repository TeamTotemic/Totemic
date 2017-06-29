package pokefenn.totemic.api.music;

/**
 * An interface for tile entities that accept music
 * Currently only really implemented by TotemBase, but you may be able to get it working with other stuff.
 */
public interface MusicAcceptor
{
    /**
     * Adds up to the given amount music of the given instrument to the acceptor
     * @return true if any music was added
     */
    boolean addMusic(MusicInstrument instr, int amount);
}
