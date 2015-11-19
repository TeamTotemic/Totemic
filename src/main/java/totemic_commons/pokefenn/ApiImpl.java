package totemic_commons.pokefenn;

import java.util.*;

import totemic_commons.pokefenn.api.TotemEffect;
import totemic_commons.pokefenn.api.TotemicAPI;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;

public final class ApiImpl implements TotemicAPI.API
{
    public final Map<String, TotemEffect> totemEffects = new HashMap<>();
    public final List<TotemEffect> totemList = new ArrayList<>();

    public final Map<String, MusicInstrument> instruments = new HashMap<>();

    public final Map<String, Ceremony> ceremonies = new HashMap<>();

    @Override
    public TotemEffect addTotem(TotemEffect effect)
    {
        if(totemEffects.containsKey(effect.getName()))
            throw new IllegalArgumentException("Duplicate Totem entry for ID " + effect.getName());
        totemEffects.put(effect.getName(), effect);
        totemList.add(effect);
        return effect;
    }

    @Override
    public TotemEffect getTotem(String name)
    {
        return totemEffects.get(name);
    }

    @Override
    public List<TotemEffect> getTotemList()
    {
        return Collections.unmodifiableList(totemList);
    }

    @Override
    public MusicInstrument addInstrument(MusicInstrument instrument)
    {
        if(instruments.containsKey(instrument.getName()))
            throw new IllegalArgumentException("Duplicate Music instrument entry for ID " + instrument.getName());
        instruments.put(instrument.getName(), instrument);
        return instrument;
    }

    @Override
    public MusicInstrument getInstrument(String name)
    {
        return instruments.get(name);
    }

    @Override
    public Ceremony addCeremony(Ceremony ceremony)
    {
        if(ceremonies.containsKey(ceremony.getName()))
            throw new IllegalArgumentException("Duplicate Ceremony entry for ID " + ceremony.getName());
        //Search for ambiguous selectors
        for(Ceremony other: ceremonies.values())
        {
            if(Arrays.equals(ceremony.getInstruments(), other.getInstruments()))
                throw new IllegalArgumentException("Could not add Ceremony " + ceremony.getName() + " because " + other.getName() +
                        " has the same musical selectors: " + Arrays.toString(ceremony.getInstruments()));
        }

        ceremonies.put(ceremony.getName(), ceremony);
        return ceremony;
    }

    @Override
    public Ceremony getCeremony(String name)
    {
        return ceremonies.get(name);
    }

}
