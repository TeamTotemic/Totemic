package totemic_commons.pokefenn.api.ceremony;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.util.StatCollector;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyRegistry
{
    private static final List<CeremonyRegistry> ceremonyList = new ArrayList<>();
    private static final Map<Integer, CeremonyRegistry> idToCeremony = new HashMap<>();

    private final int id;
    private final String name;
    private final CeremonyEffect ceremonyEffect;
    private final CeremonyActivation ceremonyActivation;

    public CeremonyRegistry(String name, int ceremonyID, CeremonyEffect ceremonyEffect, CeremonyActivation ceremonyActivation)
    {
        this.name = name;
        this.id = ceremonyID;
        this.ceremonyActivation = ceremonyActivation;
        this.ceremonyEffect = ceremonyEffect;
    }

    public static List<CeremonyRegistry> getCeremonyList()
    {
        return Collections.unmodifiableList(ceremonyList);
    }

    public static CeremonyRegistry fromId(int id)
    {
        return idToCeremony.get(id);
    }

    public static CeremonyRegistry addCeremony(CeremonyRegistry entry)
    {
        if(idToCeremony.containsKey(entry.id))
            throw new IllegalArgumentException("Duplicate Ceremony entry for ID " + entry.id + ": " + entry.name + " and " + fromId(entry.id).name);
        ceremonyList.add(entry);
        idToCeremony.put(entry.id, entry);
        return entry;
    }

    public int getCeremonyID()
    {
        return this.id;
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
