package totemic_commons.pokefenn.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.entity.projectile.EntityBaseDart;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public final class ModEntities
{


    public static void init()
    {
        //EntityRegistry.registerModEntity(EntityEfreet.class, "efreet", ConfigurationSettings.ENTITY_ID_EFREET, Totemic.instance, 100, 5, true);
        EntityRegistry.registerModEntity(EntityBaseDart.class, "totemDart", ConfigurationSettings.ENTITY_ID_DART, Totemic.instance, 80, 3, true);
        EntityRegistry.registerModEntity(EntityBuffalo.class, "totemicBuffalo", ConfigurationSettings.ENTITY_ID_BUFFALO, Totemic.instance, 80, 5, true);

        EntityRegistry.addSpawn(EntityBuffalo.class, 1000, 2, 4, EnumCreatureType.creature);

        if(ConfigurationSettings.SPAWN_EGGS)
        {
            EntityList.IDtoClassMapping.put(ConfigurationSettings.ENTITY_ID_BUFFALO, EntityBuffalo.class);
            EntityList.entityEggs.put(EntityBuffalo.class, new EntityList.EntityEggInfo(ConfigurationSettings.ENTITY_ID_BUFFALO, 0x1330, 0x1323122));
        }
    }

}
