package pokefenn.totemic.block.totem;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import pokefenn.totemic.api.WoodType;
import pokefenn.totemic.tile.totem.TileTotemBase;

public class BlockTotemBase extends BlockDirectional {
    public final WoodType woodType;

    public BlockTotemBase(WoodType woodType, Properties properties) {
        super(properties);
        this.woodType = woodType;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void fillStateContainer(Builder<Block, IBlockState> builder) {
        builder.add(FACING);
    }

    @Override
    @Nullable
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    @Nullable
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileTotemBase();
    }
}
