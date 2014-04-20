package totemic_commons.pokefenn.api.totem;

import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 13:52
 */
public interface ITotemEffect
{
    public void effect(TileTotemic totem, int upgrades, boolean intelligence, TotemRegistry totemRegistry, int horizontal, int verticle);
}
