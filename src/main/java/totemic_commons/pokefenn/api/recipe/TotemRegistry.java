package totemic_commons.pokefenn.api.recipe;

import net.minecraft.item.ItemStack;
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

    public static List<TotemRegistry> totemEffect = new ArrayList<TotemRegistry>();

    private final ItemStack totem;
    private final int chlorophyllDecrement;
    private final int verticalHight;
    private final int horizontal;
    private final ITotemEffect effect;
    private final int tier;

    public TotemRegistry(ItemStack totem, int chlorophyllDecrement, int verticalHight, int horizontal, ITotemEffect effect, int tier)
    {
        this.totem = totem;
        this.chlorophyllDecrement = chlorophyllDecrement;
        this.verticalHight = verticalHight;
        this.horizontal = horizontal;
        this.effect = effect;
        this.tier = tier;
    }

    public ItemStack getTotem()
    {
        return this.totem;
    }

    public int getChlorophyllDecrement()
    {
        return this.chlorophyllDecrement;
    }

    public int getVerticalHight()
    {
        return this.verticalHight;
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

}