package pokefenn.totemic.block.totem;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import pokefenn.totemic.api.TotemWoodType;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.util.TileUtil;

public class TotemPoleBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = Shapes.box(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);

    public final TotemWoodType woodType;
    public final TotemEffect effect;

    public TotemPoleBlock(TotemWoodType woodType, TotemEffect effect, Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
        this.woodType = woodType;
        this.effect = effect;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        if(facing == Direction.UP) {
            for(int i = 0; i < TotemEffectAPI.MAX_POLE_SIZE; i++) {
                BlockPos searchPos = currentPos.below(i + 1);
                BlockState searchState = world.getBlockState(searchPos);
                if(searchState.getBlock() instanceof TotemBaseBlock) {
                    searchState.updateShape(Direction.UP, state, world, searchPos, currentPos);
                }
                else if(!(searchState.getBlock() instanceof TotemPoleBlock))
                    break;
            }
        }
        return state;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, TileUtil.placedInWater(context));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 20;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 5;
    }

    @Override
    public MutableComponent getName() {
        return Component.translatable(this.getDescriptionId(), effect.getDisplayName());
    }

    @Override
    public String getDescriptionId() {
        return "block.totemic." + woodType.getName() + "_totem_pole";
    }

    public static int getBlockColor(int tintIndex) {
        return switch(tintIndex) {
        case 1 -> 0x555555; //Black
        case 2 -> 0xAA5555; //Red
        case 3 -> 0xAA55EE; //Purple
        case 4 -> 0xBBBB66; //Yellow
        default -> -1;
        };
    }
}
