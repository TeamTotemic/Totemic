package totemic_commons.pokefenn.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;
import totemic_commons.pokefenn.entity.projectile.EntityInvisArrow;
import totemic_commons.pokefenn.lib.Strings;

public final class ModEntities
{
    public static void init()
    {
        EntityRegistry.registerModEntity(new ResourceLocation(Totemic.MOD_ID + "." + Strings.BUFFALO_NAME), EntityBuffalo.class, Strings.BUFFALO_NAME, 0, Totemic.instance, 80, 5, true, 0x2a1c12, 0x885f3e);
        EntityRegistry.registerModEntity(new ResourceLocation(Totemic.MOD_ID + "." + Strings.BAYKOK_NAME), EntityBaykok.class, Strings.BAYKOK_NAME, 1, Totemic.instance, 80, 3, true, 0xE0E0E0, 0xF8DAD2);
        EntityRegistry.registerModEntity(new ResourceLocation(Totemic.MOD_ID + "." + Strings.INVIS_ARROW_NAME), EntityInvisArrow.class, Strings.INVIS_ARROW_NAME, 2, Totemic.instance, 64, 20, true);
    }
}
