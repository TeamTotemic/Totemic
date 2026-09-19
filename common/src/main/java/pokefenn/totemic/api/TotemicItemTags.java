package pokefenn.totemic.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Provides keys to Item tags added by Totemic.
 */
public final class TotemicItemTags {
    public static final TagKey<Item> CEDAR_LOGS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TotemicAPI.MOD_ID, "cedar_logs"));
}
