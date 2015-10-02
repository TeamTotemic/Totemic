package totemic_commons.pokefenn.lib;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import totemic_commons.pokefenn.ModBlocks;

/**
 * @author ljfa
 */
public enum WoodVariant
{
    OAK(Blocks.log, 0),
    SPRUCE(Blocks.log, 1),
    BIRCH(Blocks.log, 2),
    JUNGLE(Blocks.log, 3),
    ACACIA(Blocks.log2, 0),
    DARK_OAK(Blocks.log2, 1),
    CEDAR(ModBlocks.cedarLog, 0);

    public final Block log;
    public final int logMeta;

    private WoodVariant(Block log, int logMeta)
    {
        this.log = log;
        this.logMeta = logMeta;
    }

    public static final int count = values().length;

    /** @return The variant for the specified log block, or null if it is not a log */
    public static WoodVariant fromLog(Block block, int meta)
    {
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
