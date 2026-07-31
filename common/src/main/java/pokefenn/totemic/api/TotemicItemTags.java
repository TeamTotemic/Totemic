package pokefenn.totemic.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Provides keys to Item tags added by Totemic.
 */
public final class TotemicItemTags {
    public static final TagKey<Item> CEDAR_LOGS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(TotemicAPI.MOD_ID, "cedar_logs"));
}
