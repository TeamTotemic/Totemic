package totemic_commons.pokefenn.lib;

import java.util.Locale;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import totemic_commons.pokefenn.ModBlocks;

public enum WoodVariant implements IStringSerializable
{
    OAK(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, EnumType.OAK)),
    SPRUCE(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, EnumType.SPRUCE)),
    BIRCH(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, EnumType.BIRCH)),
    JUNGLE(Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, EnumType.JUNGLE)),
    ACACIA(Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, EnumType.ACACIA)),
    DARK_OAK(Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, EnumType.DARK_OAK)),
    CEDAR(ModBlocks.cedar_log.getDefaultState());

    public final IBlockState log;

    private WoodVariant(IBlockState log)
    {
        this.log = log;
    }

    /**
     * @return The variant for the specified log block, or {@code null} if it is not a log in this enum
     */
    @Nullable
    public static WoodVariant fromLog(IBlockState log)
    {
        Block block = log.getBlock();
        int meta = block.getMetaFromState(log);
        if(block == Blocks.LOG)
            return values()[meta & 3]; //oak - jungle
        else if(block == Blocks.LOG2)
            return values()[4 + (meta & 3)]; //acacia - dark oak
        else if(block == ModBlocks.cedar_log)
            return CEDAR;
        else
            return null;
    }

    @Override
    public String getName()
    {
        return toString().toLowerCase(Locale.ENGLISH);
    }
}
