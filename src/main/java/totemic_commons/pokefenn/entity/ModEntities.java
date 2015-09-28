package totemic_commons.pokefenn.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public final class ModEntities
{
    public static int buffalo;

    public static void init()
    {
        buffalo = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityBuffalo.class, Strings.BUFFALO_NAME, buffalo, 0x2a1c12, 0x885f3e);
        EntityRegistry.registerModEntity(EntityBuffalo.class, Strings.BUFFALO_NAME, 0, Totemic.instance, 80, 5, true);
        //EntityRegistry.addSpawn(EntityBuffalo.class, 1000, 2, 4, EnumCreatureType.creature); //No biomes to spawn in
    }
}
