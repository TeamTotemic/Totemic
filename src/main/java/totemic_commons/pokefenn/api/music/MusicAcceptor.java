package totemic_commons.pokefenn.api.music;

/**
 * An interface for tile entities that accept music
 * Currently only really implemented by TotemBase, but you may be able to get it working with other stuff.
 */
public interface MusicAcceptor
{
    /**
     * @return how much music of the given instrument the tile has
     */
    public int getMusicAmount(MusicInstrument instr);

    /**
     * Adds up to amount music of the given instrument
     */
    public void addMusic(MusicInstrument instr, int amount);

    /**
     * @return how much music of the given type the tile can accept, typically
     * at most instr.musicMaximum()
     */
    public int getMusicCapacity(MusicInstrument instr);
}
