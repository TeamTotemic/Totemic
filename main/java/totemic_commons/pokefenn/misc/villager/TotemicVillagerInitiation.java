package totemic_commons.pokefenn.misc.villager;

import cpw.mods.fml.common.registry.VillagerRegistry;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class TotemicVillagerInitiation
{
    public static int SHAMAN_VILLAGER_ID = 40000;

    public static void init()
    {
        VillagerRegistry.instance().registerVillagerId(SHAMAN_VILLAGER_ID);

        VillagerRegistry.instance().registerVillageTradeHandler(SHAMAN_VILLAGER_ID, new TotemicVillagerTrading());

    }
}
