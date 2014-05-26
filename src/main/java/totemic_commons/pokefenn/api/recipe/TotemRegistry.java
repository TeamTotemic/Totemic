package totemic_commons.pokefenn.api.recipe;

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

    public static void addTotems()
    {
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 0), Totems.DECREMENT_HORSE, 20, 20, new TotemEffectHorse(), 1));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 4), Totems.DECREMENT_SQUID, 20, 20, new TotemEffectSquid(), 1));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 2), Totems.DECREMENT_BLAZE, 20, 20, new TotemEffectBlaze(), 2));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 3), Totems.DECREMENT_OCELOT, 20, 20, new TotemEffectOcelot(), 2));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 1), Totems.DECREMENT_BAT, 20, 32, new TotemEffectBat(), 2));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 6), Totems.DECREMENT_SPIDER, 20, 20, new TotemEffectSpider(), 2));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 7), Totems.DECREMENT_COW, 18, 18, new TotemEffectCow(), 1));
    }

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