package totemic_commons.pokefenn.api;

import net.minecraft.item.ItemStack;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.api.music.MusicEnum;
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
     * @param doesNeedItems         Does it need items for when it starts up?
     * @param musicEnums            An array holding the music enums for the musical selector, has to hold 4 instruments, no more, no less.
     * @param ceremonyID            A identification integer that is used to hold the info for the Ceremony and get it back, etc.
     * @param ceremonyEffect        The actual effect the Ceremony does.
     * @param isInstant             If the effect is instant after the ceremony has done its startup, or does it continue on.
     * @param maximumTicksForEffect The maximum amount of ticks the ceremony can do it for, just put 0 if its a instant Ceremony.
     * @param item                  The Item, if you do not need items, just put this as null.
     * @param musicNeeded           The total amount of musical melody needed for the ceremony.
     * @param maximumStartupTime    The longest it can take for the startup, before the effect starts or anything.
     * @param melodyPer5After       If the ceremony is not instant, how much melody does it take ever 5?
     * @return The CeremonyRegistry of this Ceremony, save this and use the info later on.
     */
    public static CeremonyRegistry addCeremony(String name, boolean doesNeedItems, ItemStack item, MusicEnum[] musicEnums, int ceremonyID, ICeremonyEffect ceremonyEffect, boolean isInstant, int maximumTicksForEffect, int musicNeeded, int maximumStartupTime, int melodyPer5After)
    {
        CeremonyRegistry.ceremonyRegistry.add(new CeremonyRegistry(name, doesNeedItems, musicEnums[0], musicEnums[1], musicEnums[2], musicEnums[3], ceremonyID, ceremonyEffect, isInstant, maximumTicksForEffect, item, musicNeeded, maximumStartupTime, melodyPer5After));
        return CeremonyRegistry.ceremonyRegistry.get(ceremonyID);
    }
}
