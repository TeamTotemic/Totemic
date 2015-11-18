package totemic_commons.pokefenn.legacy_api.ceremony;

import totemic_commons.pokefenn.api.music.MusicInstrument;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyEffect
{
    public static final int NUM_SELECTORS = 2;

    //public static List<CeremonyEffect> ceremonyRegistry = new ArrayList<CeremonyEffect>();

    private final MusicInstrument[] musicIds;
    private final ICeremonyEffect ceremonyEffect;

    /**
     * @param ceremonyEffect The effect the ceremony does.
     * @param musicIds       An array holding the music Ids for the musical selector, has to hold NUM_SELECTORS instruments, no more, no less.
     */
    public CeremonyEffect(ICeremonyEffect ceremonyEffect, MusicInstrument[] musicIds)
    {
        if(musicIds.length != NUM_SELECTORS)
            throw new IllegalArgumentException("Wrong number of musical selectors (" + musicIds.length + ")");

        this.ceremonyEffect = ceremonyEffect;
       this.musicIds = musicIds.clone();
    }

    public MusicInstrument getMusicIds(int i)
    {
        return this.musicIds[i];
    }

    public MusicInstrument[] getMusicIds()
    {
        return this.musicIds;
    }

    public ICeremonyEffect getCeremonyEffect()
    {
        return this.ceremonyEffect;
    }
}
