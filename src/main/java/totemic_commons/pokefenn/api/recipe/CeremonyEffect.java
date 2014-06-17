package totemic_commons.pokefenn.api.recipe;

import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.api.music.MusicEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyEffect
{
    public static List<CeremonyEffect> ceremonyRegistry = new ArrayList<CeremonyEffect>();

    private final MusicEnum[] musicEnums;
    private final ICeremonyEffect ceremonyEffect;

    /**
     * @param musicEnums An array holding the music enums for the musical selector, has to hold 4 instruments, no more, no less.
     * @param ceremonyEffect The effect the ceremony does.
     */

    public CeremonyEffect(ICeremonyEffect ceremonyEffect, MusicEnum[] musicEnums)
    {
        this.ceremonyEffect = ceremonyEffect;
        this.musicEnums = musicEnums;
    }

    public MusicEnum getMusicEnums(int i)
    {
        return this.musicEnums[i];
    }

    public MusicEnum[] getMusicEnums()
    {
        return this.musicEnums;
    }

    public ICeremonyEffect getCeremonyEffect()
    {
        return this.ceremonyEffect;
    }
}
