package pokefenn.totemic.block;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import pokefenn.totemic.init.ModBlocks;

public class TipiBlock extends HorizontalDirectionalBlock {
    private static final VoxelShape SHAPE = Shapes.box(0, 0, 0, 1, 0.0625, 1);
    private static final VoxelShape VISUAL_SHAPE = Shapes.box(-1, 0, -1, 2, 6.25, 2);

    public TipiBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return canPlaceTipi(ctx) ? defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite()) : null;
    }

    private boolean canPlaceTipi(BlockPlaceContext ctx) {
        var level = ctx.getLevel();
        var pos = ctx.getClickedPos();

        var belowState = level.getBlockState(pos.below());
        if(belowState.getMaterial() != Material.GRASS && belowState.getMaterial() != Material.DIRT)
            return false;
        if(!belowState.isFaceSturdy(level, pos, Direction.UP))
            return false;

        final int radius = 1;
        final int middleHeight = 3; //the bottom 3 layers require a 3x3 of free space
        final int totalHeight = 6; //the top 3 layers only require one block of free space

        for(int y = 0; y < middleHeight; y++) {
            for(int x = -radius; x <= radius; x++) {
                for(int z = -radius; z <= radius; z++) {
                    var p = pos.offset(x, y, z);
                    if(!level.getBlockState(p).canBeReplaced(BlockPlaceContext.at(ctx, p, Direction.DOWN)))
                        return false;
                }
            }
        }
        for(int y = middleHeight; y < totalHeight; y++) {
            var p = pos.offset(0, y, 0);
            if(!level.getBlockState(p).canBeReplaced(BlockPlaceContext.at(ctx, p, Direction.DOWN)))
                return false;
        }

        return true;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        var facing = state.getValue(FACING);
        var dummyTipiState = ModBlocks.dummy_tipi.get().defaultBlockState();
        //bottom part
        for(int y = 0; y < 2; y++) {
            for(var dir: Direction.Plane.HORIZONTAL) {
                if(dir == facing)
                    continue;
                var p = pos.relative(dir).above(y);
                level.setBlock(p, dummyTipiState, Block.UPDATE_ALL | Block.UPDATE_INVISIBLE);
            }
        }
        //top part
        for(int y = 3; y < 6; y++) {
            var p = pos.above(y);
            level.setBlock(p, dummyTipiState, Block.UPDATE_ALL | Block.UPDATE_INVISIBLE);
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        var airState = Blocks.AIR.defaultBlockState();
        //bottom part
        for(int y = 0; y < 2; y++) {
            for(var dir: Direction.Plane.HORIZONTAL) {
                var p = pos.relative(dir).above(y);
                if(level.getBlockState(p).is(ModBlocks.dummy_tipi.get()))
                    level.setBlock(p, airState, Block.UPDATE_ALL | Block.UPDATE_INVISIBLE | Block.UPDATE_SUPPRESS_DROPS);
            }
        }
        //top part
        for(int y = 3; y < 6; y++) {
            var p = pos.above(y);
            if(level.getBlockState(p).is(ModBlocks.dummy_tipi.get()))
                level.setBlock(p, airState, Block.UPDATE_ALL | Block.UPDATE_INVISIBLE | Block.UPDATE_SUPPRESS_DROPS);
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return VISUAL_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
