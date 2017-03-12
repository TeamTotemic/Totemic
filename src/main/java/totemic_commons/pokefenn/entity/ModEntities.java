package totemic_commons.pokefenn.entity;

import static totemic_commons.pokefenn.Totemic.logger;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;
import totemic_commons.pokefenn.entity.projectile.EntityInvisArrow;
import totemic_commons.pokefenn.lib.Strings;

public final class ModEntities
{
    public static void init()
    {
        EntityRegistry.registerModEntity(new ResourceLocation(Totemic.MOD_ID, Strings.BUFFALO_NAME), EntityBuffalo.class, Strings.RESOURCE_PREFIX + Strings.BUFFALO_NAME, 0, Totemic.instance, 80, 5, true, 0x2A1C12, 0x885F3E);
        EntityRegistry.registerModEntity(new ResourceLocation(Totemic.MOD_ID, Strings.BAYKOK_NAME), EntityBaykok.class, Strings.RESOURCE_PREFIX + Strings.BAYKOK_NAME, 1, Totemic.instance, 80, 3, true, 0xE0E0E0, 0xF8DAD2);
        EntityRegistry.registerModEntity(new ResourceLocation(Totemic.MOD_ID, Strings.INVIS_ARROW_NAME), EntityInvisArrow.class, Strings.RESOURCE_PREFIX + Strings.INVIS_ARROW_NAME, 2, Totemic.instance, 64, 20, true);

        //Compatibility with legacy worlds from Minecraft 1.10.2
        //Doing it this way will still associate the old name with the entity class, but not the other way around.
        //TODO: Remove at some point
        logger.info("Registering compatibility for entities from legacy worlds. This will cause some \"Dangerous alternative prefix\" warnings that can be safely ignored.");
        GameRegistry.register(new EntityEntry(EntityBuffalo.class, Strings.RESOURCE_PREFIX + Strings.BUFFALO_NAME).setRegistryName("minecraft", "totemic.buffalo"));
        GameRegistry.register(new EntityEntry(EntityBaykok.class, Strings.RESOURCE_PREFIX + Strings.BAYKOK_NAME).setRegistryName("minecraft", "totemic.baykok"));
        GameRegistry.register(new EntityEntry(EntityInvisArrow.class, Strings.RESOURCE_PREFIX + Strings.INVIS_ARROW_NAME).setRegistryName("minecraft", "totemic.invis_arrow"));
    }
}
