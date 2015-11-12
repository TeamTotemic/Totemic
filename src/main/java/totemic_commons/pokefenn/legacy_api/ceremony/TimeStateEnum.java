package totemic_commons.pokefenn.legacy_api.ceremony;

import net.minecraft.util.StatCollector;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public enum TimeStateEnum
{
    INSTANT("totemic.timeState.instant"),
    ENDING_EFFECT("totemic.timeState.endingEffect");

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

