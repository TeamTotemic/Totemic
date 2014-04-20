package totemic_commons.pokefenn.recipe.registry;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.lib.Totems;
import totemic_commons.pokefenn.totem.*;

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
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 1), Totems.DECREMENT_HORSE, 10, 10, new TotemEffectHorse()));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 5), Totems.DECREMENT_SQUID, 10, 10, new TotemEffectSquid()));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 3), Totems.DECREMENT_BLAZE, 10, 10, new TotemEffectBlaze()));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 4), Totems.DECREMENT_OCELOT, 10, 10, new TotemEffectOcelot()));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 2), Totems.DECREMENT_BAT, 15, 20, new TotemEffectBat()));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 6), 0, 8, 8, new TotemEffectDraining()));
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
