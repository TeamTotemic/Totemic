package totemic_commons.pokefenn.api.ceremony;

import java.util.ArrayList;
import java.util.List;

import totemic_commons.pokefenn.api.music.MusicHandler;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyEffect
{
    public static final int NUM_SELECTORS = 2;

    //public static List<CeremonyEffect> ceremonyRegistry = new ArrayList<CeremonyEffect>();

    private final int[] musicIds;
    private final ICeremonyEffect ceremonyEffect;

    /**
     * @param ceremonyEffect The effect the ceremony does.
     * @param musicIds       An array holding the music Ids for the musical selector, has to hold NUM_SELECTORS instruments, no more, no less.
     */
    public CeremonyEffect(ICeremonyEffect ceremonyEffect, MusicHandler[] musicIds)
    {
        if(musicIds.length != NUM_SELECTORS)
            throw new IllegalArgumentException("Wrong number of musical selectors (" + musicIds.length + ")");

        this.ceremonyEffect = ceremonyEffect;
        int[] musicIdsHere = new int[NUM_SELECTORS];

        for(int i = 0; i < NUM_SELECTORS; i++)
        {
            musicIdsHere[i] = musicIds[i].getMusicId();
        }
        this.musicIds = musicIdsHere;
    }

    public int getMusicIds(int i)
    {
        return this.musicIds[i];
    }

    public int[] getMusicIds()
    {
        return this.musicIds;
    }

    public ICeremonyEffect getCeremonyEffect()
    {
        return this.ceremonyEffect;
    }
}
