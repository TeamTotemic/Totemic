package totemic_commons.pokefenn.api.ceremony;

import net.minecraft.util.StatCollector;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public enum TimeStateEnum
{
    INSTANT("totemic.timeState.instant"),
    OVER_TIME("totemic.timeState.overTime");

    private final String name;

    TimeStateEnum(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return StatCollector.translateToLocal(name);
    }
}

