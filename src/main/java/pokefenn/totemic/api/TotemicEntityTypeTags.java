package pokefenn.totemic.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

/**
 * Provides keys to EntityType tags added by Totemic.
 */
public final class TotemicEntityTypeTags {
    /**
     * Entities which can be turned into Buffaloes by the Buffalo Dance ceremony.
     */
    public static final TagKey<EntityType<?>> BUFFALO_DANCE_TARGETS = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(TotemicAPI.MOD_ID, "buffalo_dance_targets"));
    /**
     * Entities which can be turned into Bald Eagles by the Eagle Dance ceremony.
     */
    public static final TagKey<EntityType<?>> EAGLE_DANCE_TARGETS = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(TotemicAPI.MOD_ID, "eagle_dance_targets"));
}
