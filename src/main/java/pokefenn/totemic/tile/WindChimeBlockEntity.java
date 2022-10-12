package pokefenn.totemic.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import pokefenn.totemic.init.ModTileEntities;

public class WindChimeBlockEntity extends BlockEntity {
    public WindChimeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModTileEntities.wind_chime.get(), pPos, pBlockState);
    }
}
