package totemic_commons.pokefenn.entity;

import net.minecraftforge.fml.common.registry.EntityRegistry;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;
import totemic_commons.pokefenn.entity.projectile.EntityInvisArrow;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public final class ModEntities
{
    public static void init()
    {
        EntityRegistry.registerModEntity(EntityBuffalo.class, Strings.BUFFALO_NAME, 0, Totemic.instance, 80, 5, true, 0x2a1c12, 0x885f3e);
        //EntityRegistry.addSpawn(EntityBuffalo.class, 1000, 2, 4, EnumCreatureType.creature); //No biomes to spawn in

        EntityRegistry.registerModEntity(EntityBaykok.class, Strings.BAYKOK_NAME, 1, Totemic.instance, 80, 3, true, 0xFFFFFF, 0xFFFFFF); //TODO: Egg colors
        EntityRegistry.registerModEntity(EntityInvisArrow.class, Strings.INVIS_ARROW_NAME, 2, Totemic.instance, 64, 20, true);
    }
}
