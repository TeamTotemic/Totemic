package totemic_commons.pokefenn.api.music;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public enum MusicEnum
{
    FLUTE_MUSIC("Flute"),
    DRUM_MUSIC("Drum"),
    WIND_CHIME_MUSIC("Wind Chime"),
    SYNTHESIZER_MUSIC("Synthesizer"),
    GONG_MUSIC("Gong");

    private final String name;

    MusicEnum(String name)
    {
        this.name = name;
    }

    String getName()
    {
        return this.name;
    }
}



