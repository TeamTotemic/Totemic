package pokefenn.totemic.block.totem;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer.Builder;
import pokefenn.totemic.api.WoodType;
import pokefenn.totemic.api.totem.TotemEffect;

public class BlockTotemPole extends BlockDirectional {
    public final WoodType woodType;
    public final TotemEffect effect;

    public BlockTotemPole(WoodType woodType, TotemEffect effect, Properties properties) {
        super(properties);
        this.woodType = woodType;
        this.effect = effect;
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
}
