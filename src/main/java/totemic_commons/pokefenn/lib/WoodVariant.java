package totemic_commons.pokefenn.lib;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import totemic_commons.pokefenn.ModBlocks;

/**
 * @author ljfa
 */
public enum WoodVariant {
    OAK,
    SPRUCE,
    BIRCH,
    JUNGLE,
    ACACIA,
    DARK_OAK,
    CEDAR;

    public static final int count = values().length;

    /** @return The variant for the specified log block, or null if it is not a log */
    public static WoodVariant fromLog(Block block, int meta) {
        if(block == Blocks.log)
            return values()[meta & 3]; //oak - jungle
        else if(block == Blocks.log2)
            return values()[4 + (meta & 3)]; //acacia - dark oak
        else if(block == ModBlocks.cedarLog)
            return CEDAR;
        else
            return null;
    }
}
