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
    BELL_SHOE_MUSIC("Bell Shoes"),
    GONG_MUSIC("Gong");

    private final String musicName;

    MusicEnum(String musicName)
    {
        this.musicName = musicName;
    }

    String getMusicName()
    {
        return this.musicName;
    }
}



