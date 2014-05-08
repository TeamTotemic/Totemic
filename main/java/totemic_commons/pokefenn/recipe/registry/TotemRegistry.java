package totemic_commons.pokefenn.recipe.registry;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.*;
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
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 0), Totems.DECREMENT_HORSE, 20, 20, new TotemEffectHorse()));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 4), Totems.DECREMENT_SQUID, 20, 20, new TotemEffectSquid()));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 2), Totems.DECREMENT_BLAZE, 20, 20, new TotemEffectBlaze()));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 3), Totems.DECREMENT_OCELOT, 20, 20, new TotemEffectOcelot()));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 1), Totems.DECREMENT_BAT, 20, 32, new TotemEffectBat()));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 5), 0, 8, 8, new TotemEffectDraining()));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 6), Totems.DECREMENT_SPIDER, 20, 20, new TotemEffectSpider()));
        totemEffect.add(new TotemRegistry(new ItemStack(ModItems.totems, 1, 7), Totems.DECREMENT_COW, 18, 18, new TotemEffectCow()));
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
