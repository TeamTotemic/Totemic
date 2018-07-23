package pokefenn.totemic.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.lib.Strings;

public class BlockCedarLog extends BlockLog
{
    public BlockCedarLog()
    {
        setRegistryName(Strings.CEDAR_LOG_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.CEDAR_LOG_NAME);
        setHardness(2F);
        setResistance(5F);
        setCreativeTab(Totemic.tabsTotem);
        setDefaultState(getDefaultState().withProperty(LOG_AXIS, EnumAxis.Y));
        Blocks.FIRE.setFireInfo(this, 5, 5);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        switch(state.getValue(LOG_AXIS))
        {
        case Y:
            return MapColor.PINK;
        default:
            return MapColor.ADOBE;
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, LOG_AXIS);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = 0;
        switch(state.getValue(LOG_AXIS))
        {
            case Y:
                break;
            case X:
                meta |= 4;
                break;
            case Z:
                meta |= 8;
                break;
            case NONE:
                meta |= 12;
        }

        return meta;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = getDefaultState();
        switch(meta)
        {
            case 0:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
                break;
            case 4:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
                break;
            case 8:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
                break;
            default:
                state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
        }
        return state;
    }
}
