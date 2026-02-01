package totemic_commons.pokefenn.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.entity.animal.EntityBaldEagle;
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
        EntityRegistry.registerModEntity(EntityBuffalo.class, Strings.BUFFALO_NAME, 0, Totemic.instance, 80, 5, true);
        EntityRegistry.registerModEntity(EntityBaykok.class, Strings.BAYKOK_NAME, 1, Totemic.instance, 80, 5, true);
        EntityRegistry.registerModEntity(EntityInvisArrow.class, Strings.INVIS_ARROW_NAME, 2, Totemic.instance, 64, 20, false);
        EntityRegistry.registerModEntity(EntityBaldEagle.class, Strings.BALD_EAGLE_NAME, 3, Totemic.instance, 80, 3, true);
    }
}
