package totemic_commons.pokefenn;

import java.util.*;

import totemic_commons.pokefenn.api.TotemEffect;
import totemic_commons.pokefenn.api.TotemicAPI;

public class ApiImpl implements TotemicAPI.API
{
    public final Map<String, TotemEffect> totemEffects = new HashMap<>();
    public final List<TotemEffect> totemList = new ArrayList<>();

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

}
