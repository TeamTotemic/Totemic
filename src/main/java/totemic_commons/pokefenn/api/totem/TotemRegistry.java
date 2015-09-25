package totemic_commons.pokefenn.api.totem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.util.StatCollector;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 10:33
 */
public class TotemRegistry
{
    private static final List<TotemRegistry> effectsList = new ArrayList<>();
    private static final TIntObjectMap<TotemRegistry> idToEffects = new TIntObjectHashMap<>();

    private final int id;
    private final int verticalHeight;
    private final int horizontal;
    private final ITotemEffect effect;
    private final int tier;
    private final String name;

    public TotemRegistry(int id, int verticalHeight, int horizontal, ITotemEffect effect, int tier, String name)
    {
        this.id = id;
        this.verticalHeight = verticalHeight;
        this.horizontal = horizontal;
        this.effect = effect;
        this.tier = tier;
        this.name = name;
    }

    public static List<TotemRegistry> getTotemList()
    {
        return Collections.unmodifiableList(effectsList);
    }

    public static TotemRegistry fromId(int id)
    {
        return idToEffects.get(id);
    }

    public static TotemRegistry addTotem(TotemRegistry entry)
    {
        if(idToEffects.containsKey(entry.id))
            throw new IllegalArgumentException("Duplicate Totem entry for ID " + entry.id + ": " + entry.name + " and " + fromId(entry.id).name);
        effectsList.add(entry);
        idToEffects.put(entry.id, entry);
        return entry;
    }

    public int getTotemId()
    {
        return this.id;
    }

    public int getVerticalHeight()
    {
        return this.verticalHeight;
    }

    public int getHorizontal()
    {
        return this.horizontal;
    }

    public ITotemEffect getEffect()
    {
        return this.effect;
    }

    public int getTier()
    {
        return this.tier;
    }

    public String getName()
    {
        return this.name;
    }

    public String getLocalizedName()
    {
        return StatCollector.translateToLocal(name);
    }
}