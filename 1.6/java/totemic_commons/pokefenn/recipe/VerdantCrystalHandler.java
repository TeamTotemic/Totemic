package totemic_commons.pokefenn.recipe;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.verdant.IVerdantCrystalEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 10/03/14
 * Time: 19:11
 */
public class VerdantCrystalHandler
{


    public static List<VerdantCrystalHandler> verdantCrystalHandler = new ArrayList<VerdantCrystalHandler>();

    public static void addRecipes()
    {
        verdantCrystalHandler.add(new VerdantCrystalHandler(new ItemStack(ModItems.chlorophyllCrystal), null));
    }

    private final ItemStack crystal;
    private final IVerdantCrystalEffect effect;

    public VerdantCrystalHandler(ItemStack crystal, IVerdantCrystalEffect effect)
    {
        this.crystal = crystal;
        this.effect = effect;
    }

    public ItemStack getCrystal()
    {
        return this.crystal;
    }

    public IVerdantCrystalEffect getEffect()
    {
        return this.effect;
    }

    public static List<VerdantCrystalHandler> getRecipes()
    {
        return verdantCrystalHandler;
    }

}
