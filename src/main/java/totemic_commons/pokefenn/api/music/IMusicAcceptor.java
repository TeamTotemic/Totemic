package totemic_commons.pokefenn.api.music;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public interface IMusicAcceptor
{
    /**
     * A interface for blocks that accept music and stuff.
     * Currently this will only really interface with the TotemBase, but you may be able to get it working with other stuff.
     */

    /**
     * @return An array holding the values of how much Musical Melody you have in a tile entity
     */
    public int[] getMusicArray();

    /**
     * @return The selector that is used to determine which Ceremony to play, just return a default value if not used.
     */
    public int[] getMusicSelector();

    /**
     * @return Is this a tile entity which needs to do different effects depending on how instruments were played? If so, true, else, false.
     */
    public boolean doesMusicSelect();

    /**
     * @return The variable which says "Are you music selecting currently".
     */
    public boolean isMusicSelecting();

    /**
     * @return A variable that will increased when playing music out of a array environment.
     */
    public int getMusicForEffect();

    /**
     * @return If the block is doing a ceremony currently.
     */
    public boolean getIsCeremony();


    /**
     * @return If its doing a ending effect and stuff.
     */
    public boolean isDoingEndingEffect();
}
