package totemic_commons.pokefenn.api.ceremony;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.StatCollector;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyRegistry
{
    public static List<CeremonyRegistry> ceremonyRegistry = new ArrayList<CeremonyRegistry>();

    private final int ceremonyID;
    private final String name;
    private final CeremonyEffect ceremonyEffect;
    private final CeremonyActivation ceremonyActivation;

    public CeremonyRegistry(String name, int ceremonyID, CeremonyEffect ceremonyEffect, CeremonyActivation ceremonyActivation)
    {
        this.name = name;
        this.ceremonyID = ceremonyID;
        this.ceremonyActivation = ceremonyActivation;
        this.ceremonyEffect = ceremonyEffect;
    }

    public int getCeremonyID()
    {
        return this.ceremonyID;
    }

    public String getName()
    {
        return this.name;
    }

    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(name);
    }

    public CeremonyActivation getCeremonyActivation()
    {
        return this.ceremonyActivation;
    }

    public CeremonyEffect getCeremonyEffect()
    {
        return this.ceremonyEffect;
    }

}
