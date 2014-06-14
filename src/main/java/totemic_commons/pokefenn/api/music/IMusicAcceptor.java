package totemic_commons.pokefenn.api.music;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public interface IMusicAcceptor
{
    /**
     * @return An array holding the values of how much Musical Melody you have in a tile entity
     */
    public int[] getMusicArray();

    /**
     * @return The selector that is used to determin which Ceremony to play, just return a default value if not used.
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
     * @return A variable that will increased when playing music out of a array enviroment.
     */
    public int getMusicForEffect();

    public boolean getEffectMusic();
}
