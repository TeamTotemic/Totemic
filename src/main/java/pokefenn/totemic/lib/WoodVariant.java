package pokefenn.totemic.lib;

import java.util.Locale;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import pokefenn.totemic.init.ModBlocks;

public enum WoodVariant implements IStringSerializable
{
    OAK(EnumType.OAK.getMapColor()),
    SPRUCE(EnumType.SPRUCE.getMapColor()),
    BIRCH(EnumType.BIRCH.getMapColor()),
    JUNGLE(EnumType.JUNGLE.getMapColor()),
    ACACIA(EnumType.ACACIA.getMapColor()),
    DARK_OAK(EnumType.DARK_OAK.getMapColor()),
    CEDAR(MapColor.PINK);

    private final String name = toString().toLowerCase(Locale.ROOT);
    private final MapColor mapColor;

    private WoodVariant(MapColor mapColor)
    {
        this.mapColor = mapColor;
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

    public static WoodVariant fromID(int id)
    {
        if(id < 0 || id >= values().length)
            id = 0;
        return values()[id];
    }

    public int getID()
    {
        return ordinal();
    }

    @Override
    public String getName()
    {
        return name;
    }

    public MapColor getMapColor()
    {
        return mapColor;
    }
}
