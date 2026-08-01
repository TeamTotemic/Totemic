package pokefenn.totemic.block.totem;

import java.util.Optional;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModDataComponents;
import pokefenn.totemic.item.TotemPoleItem;
import pokefenn.totemic.util.BlockUtil;

public class TotemPoleBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock {
    public static final MapCodec<TotemPoleBlock> CODEC = simpleCodec(TotemPoleBlock::new);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = Block.box(2, 0, 2,  14, 16, 14);

    public TotemPoleBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if(facing == Direction.UP) {
            findTotemBase(level, currentPos)
                    .ifPresent(TotemBaseBlockEntity::onPoleChange);
        }
        BlockUtil.scheduleWaterloggedTick(state, currentPos, level);
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        var woodType = TotemPoleItem.getWoodType(stack);
        var carving = TotemPoleItem.getCarving(stack);
        level.getBlockEntity(pos, ModBlockEntities.totem_pole.get())
                .ifPresent(pole -> pole.setAppearance(woodType, carving));
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
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new TotemPoleBlockEntity(pPos, pState);
    }

    // Soft-overrides Neo extension method in IBlockExtension
    public MapColor getMapColor(BlockState state, BlockGetter level, BlockPos pos, MapColor defaultColor) {
        return level.getBlockEntity(pos, ModBlockEntities.totem_pole.get())
                .map(pole -> pole.getWoodType().woodColor())
                .orElse(defaultColor);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        var tile = pLevel.getBlockEntity(pPos, ModBlockEntities.totem_pole.get());
        var woodType = tile.map(TotemPoleBlockEntity::getWoodType).orElseGet(ModContent.oak);
        var carving = tile.map(TotemPoleBlockEntity::getCarving).orElseGet(ModContent.none);
        var stack = new ItemStack(this);
        stack.set(ModDataComponents.WOOD_TYPE.get(), woodType);
        stack.set(ModDataComponents.CARVING.get(), carving);
        return stack;
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
}
