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
    /**
     * @param totem         The tile entity of the Totem.
     * @param upgrades      The amount of range upgrades (Not implemented, currently)
     * @param intelligence  Does it run off plant essence?
     * @param totemRegistry The TotemRegistry, use it to gather information about the Totem.
     * @param horizontal    The Horizontal radius.
     * @param verticle      The Vertical radius.
     * @param melody        How much musical melody does it have?
     */
    public void effect(TileTotemic totem, int upgrades, boolean intelligence, TotemRegistry totemRegistry, int horizontal, int verticle, int melody);
}
