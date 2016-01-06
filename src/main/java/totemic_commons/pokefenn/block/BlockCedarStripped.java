package totemic_commons.pokefenn.block;

import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockCedarStripped extends BlockLog
{
    public BlockCedarStripped()
    {
        setUnlocalizedName(Strings.RED_CEDAR_STRIPPED_NAME);
        setHardness(1.5F);
        setCreativeTab(Totemic.tabsTotem);
        setTickRandomly(true);
        setDefaultState(getDefaultState().withProperty(LOG_AXIS, EnumAxis.Y));
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
    {
        if(!world.isRemote)
        {
            if(random.nextInt(20) == 0) //about once every 15-20 minutes
            {
                Material mat = world.getBlockState(pos.down()).getBlock().getMaterial();
                if(mat == Material.ground || mat == Material.grass)
                {
                    world.setBlockState(pos, getDefaultState());
                }
            }
        }
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, LOG_AXIS);
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
