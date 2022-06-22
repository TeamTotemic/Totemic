package pokefenn.totemic.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import pokefenn.totemic.api.TotemicAPI;

public final class ModBlockTags { //TODO: Replace by dynamically created tags if possible
    public static final TagKey<Block> TOTEM_BASES = BlockTags.create(new ResourceLocation(TotemicAPI.MOD_ID, "totem_bases"));
    public static final TagKey<Block> TOTEM_POLES = BlockTags.create(new ResourceLocation(TotemicAPI.MOD_ID, "totem_poles"));
    public static final TagKey<Block> CEDAR_LOGS = BlockTags.create(new ResourceLocation(TotemicAPI.MOD_ID, "cedar_logs"));
}
