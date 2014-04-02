package totemic_commons.pokefenn.totem;

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

        //totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1), 2, 10, 10, new TotemEffectHorse()));

    }

    public static List<TotemRegistry> totemEffect = new ArrayList<TotemRegistry>();

    private final ItemStack totem;
    private final int chlorophyllDecrement;
    private final int verticalHight;
    private final int horizontal;
    private final ITotemEffect effect;

    public TotemRegistry(ItemStack totem, int chlorophyllDecrement, int verticalHight, int horizontal, ITotemEffect effect)
    {
        this.totem = totem;
        this.chlorophyllDecrement = chlorophyllDecrement;
        this.verticalHight = verticalHight;
        this.horizontal = horizontal;
        this.effect = effect;
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
}
