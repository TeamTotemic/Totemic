package totemic_commons.pokefenn.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.entity.spirit.boss.EntityEfreet;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ModEntities
{


    public static void init(Object totemic)
    {
        EntityRegistry.registerModEntity(EntityEfreet.class, "efreet", ConfigurationSettings.ENTITY_ID_EFREET, totemic, 100, 5, true);
    }

}
