package totemic_commons.pokefenn.api.ceremony;

import totemic_commons.pokefenn.api.music.MusicHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyEffect
{
    public static List<CeremonyEffect> ceremonyRegistry = new ArrayList<CeremonyEffect>();

    private final int[] musicIds;
    private final ICeremonyEffect ceremonyEffect;

    /**
     * @param musicIds       An array holding the music Ids for the musical selector, has to hold 4 instruments, no more, no less.
     * @param ceremonyEffect The effect the ceremony does.
     */

    public CeremonyEffect(ICeremonyEffect ceremonyEffect, MusicHandler[] musicIds)
    {
        this.ceremonyEffect = ceremonyEffect;

        int[] musicIdsHere = new int[4];

        for(int i = 0; i < musicIds.length; i++)
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
