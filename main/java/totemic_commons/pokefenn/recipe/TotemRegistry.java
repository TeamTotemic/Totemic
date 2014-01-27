package totemic_commons.pokefenn.recipe;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.api.ITotemEffect;

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
    //Todo get this working, this will allow for api/imc message totem effects

    public static void addTotems()
    {


    }

    public static List<TotemRegistry> totemEffect = new ArrayList<TotemRegistry>();

    private final ItemStack input;
    private final int chlorophyllDecrement;
    private final ITotemEffect effect;

    public TotemRegistry(ItemStack input, int chlorophyllDecrement, ITotemEffect effect)
    {
        this.input = input;
        this.chlorophyllDecrement = chlorophyllDecrement;
        this.effect = effect;
    }

    public ItemStack getInput()
    {
        return this.input;
    }

    public int getChlorophyllDecrement()
    {
        return this.chlorophyllDecrement;
    }

    public ITotemEffect getEffect()
    {
        return this.effect;
    }

    public static List<TotemRegistry> getRecipes()
    {
        return totemEffect;
    }
}
