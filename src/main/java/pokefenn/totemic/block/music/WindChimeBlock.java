package pokefenn.totemic.block.music;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import pokefenn.totemic.tile.WindChimeBlockEntity;

public class WindChimeBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {
    public WindChimeBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WindChimeBlockEntity(pPos, pState);
    }
}
