package pokefenn.totemic.lib;

import java.util.Locale;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import pokefenn.totemic.ModBlocks;

public enum WoodVariant implements IStringSerializable
{
    OAK,
    SPRUCE,
    BIRCH,
    JUNGLE,
    ACACIA,
    DARK_OAK,
    CEDAR;

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
        return toString().toLowerCase(Locale.ROOT);
    }
}
