package pokefenn.totemic.block.totem;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.totem.TotemEffect;

public class BlockTotemPole extends HorizontalBlock {
    public final TotemWoodType woodType;
    public final TotemEffect effect;

    public BlockTotemPole(TotemWoodType woodType, TotemEffect effect, Properties properties) {
        super(properties);
        this.woodType = woodType;
        this.effect = effect;
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
}
