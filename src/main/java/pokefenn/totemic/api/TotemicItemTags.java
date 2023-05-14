package pokefenn.totemic.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * Provides keys to Item tags added by Totemic.
 */
public final class TotemicItemTags {
    public static final TagKey<Item> CEDAR_LOGS = ItemTags.create(new ResourceLocation(TotemicAPI.MOD_ID, "cedar_logs"));
}
