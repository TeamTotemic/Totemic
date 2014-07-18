package totemic_commons.pokefenn.api.recipe;

import totemic_commons.pokefenn.api.totem.ITotemEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 10:33
 */
public class TotemRegistry
{

    public static ArrayList<TotemRegistry> totemEffect = new ArrayList<TotemRegistry>();

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

    public static List<TotemRegistry> getRecipes()
    {
        return totemEffect;
    }

    public int getTier()
    {
        return this.tier;
    }

    public String getName()
    {
        return this.name;
    }

}