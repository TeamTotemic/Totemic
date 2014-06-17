package totemic_commons.pokefenn.api;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.api.recipe.CeremonyActivation;
import totemic_commons.pokefenn.api.recipe.CeremonyEffect;
import totemic_commons.pokefenn.api.recipe.CeremonyRegistry;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.api.totem.ITotemEffect;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 05/03/14
 * Time: 19:53
 */
public class TotemicAPI
{

    /**
     * @param id         A identification for your Totem
     * @param totem      The ItemStack of the Totem
     * @param vertical   The vertical radius of the Totem, this is the base
     * @param horizontal The horizontal radius of the Totem, this is the base
     * @param effect     The TotemEffect
     * @param tier       The tier, currently not used, so just put 1 or something
     * @param name       The name of the Totem
     * @return The TotemRegistry, you will then be able to store this to use it for later information
     */
    public static TotemRegistry addTotem(int id, ItemStack totem, int vertical, int horizontal, ITotemEffect effect, int tier, String name)
    {
        TotemRegistry.getRecipes().add(new TotemRegistry(totem, vertical, horizontal, effect, tier, name));
        return TotemRegistry.getRecipes().get(id);
    }

    /**
     * @param name                  The name of the Ceremony, this will be displayed and such.
     * @param ceremonyID            A identification integer that is used to hold the info for the Ceremony and get it back, etc.
     * @param ceremonyActivation    This holds information on what is needed to activate the ceremony.
     * @param ceremonyEffect        This holds information on what effect the Ceremony does.
     * @return The CeremonyRegistry of this Ceremony, save this and use the info later on.
     */
    public static CeremonyRegistry addCeremony(String name, int ceremonyID, CeremonyEffect ceremonyEffect, CeremonyActivation ceremonyActivation)
    {
        CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry(name, ceremonyID, ceremonyEffect, ceremonyActivation));
        return CeremonyRegistry.ceremonyRegistry.get(ceremonyID);
    }
}
