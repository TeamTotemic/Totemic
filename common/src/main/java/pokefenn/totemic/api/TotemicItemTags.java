package pokefenn.totemic.api;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Provides keys to Item tags added by Totemic.
 */
public final class TotemicItemTags {
    /**
     * Contains items that can be used to repair a Jingle Dress in an anvil.
     */
    public static final TagKey<Item> REAPIRS_JINGLE_DRESS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TotemicAPI.MOD_ID, "repairs_jingle_dress"));

    public static final TagKey<Item> CEDAR_LOGS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(TotemicAPI.MOD_ID, "cedar_logs"));
}
