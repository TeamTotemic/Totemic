package pokefenn.totemic.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TotemTorchBlock extends TorchBlock {
    protected static final VoxelShape AABB = Block.box(4.75, 0, 4.75, 11.25, 16, 11.25);

    public TotemTorchBlock(Properties pProperties) {
        super(ParticleTypes.FLAME, pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        double x = pPos.getX() + 0.5D;
        double y = pPos.getY() + 1.0D;
        double z = pPos.getZ() + 0.5D;
        for(int i = 0; i < 2; i++)
            pLevel.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 0.0D, 0.0D);
        for(int i = 0; i < 2; i++)
            pLevel.addParticle(this.flameParticle, x, y, z, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AABB;
    }
}
