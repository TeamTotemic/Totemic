package pokefenn.totemic.block.totem;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.tile.totem.TileTotemBase;

public class BlockTotemBase extends HorizontalBlock {
    public final TotemWoodType woodType;

    public BlockTotemBase(TotemWoodType woodType, Properties properties) {
        super(properties);
        this.woodType = woodType;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    @Nullable
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileTotemBase();
    }
}
