package totemic_commons.pokefenn.entity;

import net.minecraft.entity.EntityList;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public final class ModEntities
{
    public static void init()
    {
        EntityList.stringToClassMapping.put("totemic." + Strings.BUFFALO_NAME, EntityBuffalo.class);
        EntityRegistry.registerModEntity(EntityBuffalo.class, Strings.BUFFALO_NAME, 0, Totemic.instance, 80, 5, true);
        //EntityRegistry.addSpawn(EntityBuffalo.class, 1000, 2, 4, EnumCreatureType.creature); //No biomes to spawn in
    }
}
