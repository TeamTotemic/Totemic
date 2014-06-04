package totemic_commons.pokefenn.api.music;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public enum MusicEnum
{
    FLUTE("Flute"),
    DRUM("Drum"),
    WIND_CHIME("Wind Chime"),
    JINGLE_DRESS("Jingle Dress");

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



