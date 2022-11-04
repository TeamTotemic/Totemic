package pokefenn.totemic.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

/**
 * Provides keys to Block tags added by Totemic.
 */
public final class TotemicBlockTags {
    public static final TagKey<Block> TOTEM_BASES = BlockTags.create(new ResourceLocation(TotemicAPI.MOD_ID, "totem_bases"));
    public static final TagKey<Block> TOTEM_POLES = BlockTags.create(new ResourceLocation(TotemicAPI.MOD_ID, "totem_poles"));
    public static final TagKey<Block> CEDAR_LOGS = BlockTags.create(new ResourceLocation(TotemicAPI.MOD_ID, "cedar_logs"));
}
