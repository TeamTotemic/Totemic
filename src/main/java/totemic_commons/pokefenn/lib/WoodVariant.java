package totemic_commons.pokefenn.lib;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import totemic_commons.pokefenn.ModBlocks;

/**
 * @author ljfa
 */
public enum WoodVariant
{
    OAK(Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, EnumType.OAK)),
    SPRUCE(Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, EnumType.SPRUCE)),
    BIRCH(Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, EnumType.BIRCH)),
    JUNGLE(Blocks.log.getDefaultState().withProperty(BlockOldLog.VARIANT, EnumType.JUNGLE)),
    ACACIA(Blocks.log2.getDefaultState().withProperty(BlockNewLog.VARIANT, EnumType.ACACIA)),
    DARK_OAK(Blocks.log2.getDefaultState().withProperty(BlockNewLog.VARIANT, EnumType.DARK_OAK)),
    CEDAR(ModBlocks.cedarLog.getDefaultState());

    public final IBlockState log;

    private WoodVariant(IBlockState log)
    {
        this.log = log;
    }

    public static final int count = values().length;

    /** @return The variant for the specified log block, or null if it is not a log */
    public static WoodVariant fromLog(IBlockState log)
    {
        Block block = log.getBlock();
        int meta = block.getMetaFromState(log);
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
