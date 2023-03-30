package pokefenn.totemic.block.totem;

import java.util.Objects;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
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
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.item.TotemKnifeItem;
import pokefenn.totemic.util.BlockUtil;

public class TotemPoleBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = Shapes.box(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);

    public final TotemWoodType woodType;

    public TotemPoleBlock(TotemWoodType woodType, Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
        this.woodType = woodType;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if(facing == Direction.UP) {
            findTotemBase(level, currentPos)
                    .ifPresent(TotemBaseBlockEntity::onPoleChange);
        }
        BlockUtil.scheduleWaterloggedTick(state, currentPos, level);
        return state;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        var carving = Objects.requireNonNullElse(TotemKnifeItem.getCarving(stack), ModContent.none);
        level.getBlockEntity(pos, ModBlockEntities.totem_pole.get())
                .ifPresent(pole -> pole.setCarving(carving));
        findTotemBase(level, pos)
                .ifPresent(TotemBaseBlockEntity::onPoleChange);
    }

    private Optional<TotemBaseBlockEntity> findTotemBase(BlockGetter level, BlockPos currentPos) {
        for(int i = 0; i < TotemEffectAPI.MAX_POLE_SIZE; i++) {
            var searchPos = currentPos.below(i + 1);
            var blockEntity = level.getBlockEntity(searchPos);
            if(blockEntity instanceof TotemBaseBlockEntity base)
                return Optional.of(base);
            else if(!(blockEntity instanceof TotemPoleBlockEntity))
                break;
        }
        return Optional.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, BlockUtil.placedInWater(context));
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
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TotemPoleBlockEntity(pPos, pState);
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
