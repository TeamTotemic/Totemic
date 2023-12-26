package pokefenn.totemic.block;

import java.util.Optional;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import pokefenn.totemic.init.ModBlocks;

public class DummyTipiBlock extends Block {
    public DummyTipiBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        findMainTipiBlock(level, pos).ifPresent(pair -> {
            var tipiPos = pair.getFirst();
            var tipiState = pair.getSecond();

            tipiState.getBlock().playerWillDestroy(level, tipiPos, tipiState, player);
            if(!player.isCreative())
                dropResources(tipiState, level, tipiPos, null, player, player.getMainHandItem());
            level.setBlock(tipiPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
        });
        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void wasExploded(Level level, BlockPos pos, Explosion explosion) {
        findMainTipiBlock(level, pos).ifPresent(pair -> {
            var tipiPos = pair.getFirst();
            var tipiState = pair.getSecond();

            tipiState.onBlockExploded(level, tipiPos, explosion); //will remove the Tipi block
        });
        super.wasExploded(level, pos, explosion);
    }

    private Optional<Pair<BlockPos, BlockState>> findMainTipiBlock(Level level, BlockPos pos) {
        //if the dummy tipi is in the bottom part
        for(int y = 0; y < 2; y++) {
            for(var dir: Direction.Plane.HORIZONTAL) {
                var p = pos.relative(dir).below(y);
                var state = level.getBlockState(p);
                if(state.is(ModBlocks.tipi.get()))
                    return Optional.of(Pair.of(p, state));
            }
        }
        //if it is in the top part
        for(int y = 3; y < 6; y++) {
            var p = pos.below(y);
            var state = level.getBlockState(p);
            if(state.is(ModBlocks.tipi.get()))
                return Optional.of(Pair.of(p, state));
        }
        return Optional.empty();
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return new ItemStack(ModBlocks.tipi.get());
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.BLOCK;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }

    @Override
    public VoxelShape getVisualShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }
}
