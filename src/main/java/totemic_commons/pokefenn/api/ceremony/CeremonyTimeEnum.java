package totemic_commons.pokefenn.api.ceremony;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public enum CeremonyTimeEnum
{
    NO_TIME(0),
    VERY_SHORT(5 * 20),
    SHORT(15 * 20),
    MEDIUM(30 * 20),
    LONG(45 * 20),
    EXTRA_LONG(60 * 20),
    MEDIUM_STUPID_LONG(90 * 20),
    STUPIDLY_LONG(120 * 20);

    private final int time;

    CeremonyTimeEnum(int time)
    {
        this.time = time;
    }

    public int getTime()
    {
        return this.time;
    }
}
