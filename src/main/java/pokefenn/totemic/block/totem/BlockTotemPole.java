package pokefenn.totemic.block.totem;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.StateContainer.Builder;

public class BlockTotemPole extends BlockDirectional {
    public BlockTotemPole(Properties properties) {
        super(properties);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void fillStateContainer(Builder<Block, IBlockState> builder) {
        builder.add(FACING);
    }
}
