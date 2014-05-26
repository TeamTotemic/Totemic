package totemic_commons.pokefenn.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.entity.projectile.EntityBaseDart;
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
        EntityRegistry.registerModEntity(EntityBaseDart.class, "totemDart", ConfigurationSettings.ENTITY_ID_DART, totemic, 80, 5, true);
        //TODO in the future, meh
        EntityRegistry.registerModEntity(EntityAkuAku.class, "akuAku", 50, totemic, 80, 5, true);
    }

}
