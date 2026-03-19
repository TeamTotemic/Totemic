package pokefenn.totemic.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;

/**
 * Provides keys to Block tags added by Totemic.
 */
public final class TotemicBlockTags {
    /**
     * Contains all kinds of Red Cedar Logs
     */
    public static final TagKey<Block> CEDAR_LOGS = BlockTags.create(ResourceLocation.fromNamespaceAndPath(TotemicAPI.MOD_ID, "cedar_logs"));

    /**
     * Contains all sorts of plants that the Zaphkiel Waltz ceremony may affect.
     * However, only block states which are {@linkplain BlockStateBase#isRandomlyTicking() randomly ticking} will actually be affected.
     */
    public static final TagKey<Block> ZAPHKIEL_WALTZ_GROWABLE = BlockTags.create(ResourceLocation.fromNamespaceAndPath(TotemicAPI.MOD_ID, "zaphkiel_waltz_growable"));

    /**
     * Tipis can only be placed on blocks tagged with this.
     */
    public static final TagKey<Block> SUPPORTS_TIPI = BlockTags.create(ResourceLocation.fromNamespaceAndPath(TotemicAPI.MOD_ID, "supports_tipi"));
}
