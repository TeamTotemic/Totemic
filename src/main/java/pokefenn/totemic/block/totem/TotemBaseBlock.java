package pokefenn.totemic.block.totem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import pokefenn.totemic.api.totem.TotemEffectAPI;
import pokefenn.totemic.block.totem.entity.StateCeremonyEffect;
import pokefenn.totemic.block.totem.entity.StateSelection;
import pokefenn.totemic.block.totem.entity.StateStartup;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.item.TotemPoleItem;
import pokefenn.totemic.util.BlockUtil;

public class TotemBaseBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = Shapes.or(Shapes.box(0.0, 0.0, 0.0,  1.0, 0.28125, 1.0), Shapes.box(0.125, 0.28125, 0.125,  0.875, 1.0, 0.875));

    public TotemBaseBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        var stack = player.getItemInHand(hand);
        if(stack.is(ModItems.totemic_staff.get()))
            return onTotemicStaffRightClick(level, pos, player);
        else
            return InteractionResult.PASS;
    }

    @SuppressWarnings("resource")
    private InteractionResult onTotemicStaffRightClick(Level level, BlockPos pos, Player player) {
        if(!level.isClientSide)
            return InteractionResult.CONSUME;

        level.getBlockEntity(pos, ModBlockEntities.totem_base.get())
        .ifPresent(tile -> {
            if(tile.getTotemState() instanceof StateTotemEffect state) {
                player.displayClientMessage(Component.translatable("totemic.isDoingNoCeremony"), false);
                if(Minecraft.getInstance().options.advancedItemTooltips)
                    player.displayClientMessage(Component.translatable("totemic.totemEffectMusic", state.getTotemEffectMusic(), TotemEffectAPI.MAX_TOTEM_EFFECT_MUSIC).withStyle(ChatFormatting.GRAY), false);
            }
            else if(tile.getTotemState() instanceof StateSelection state) {
                String selectors = state.getSelectors().stream()
                        .map(instr -> instr.getDisplayName().getString())
                        .collect(Collectors.joining(", "));
                player.displayClientMessage(Component.translatable("totemic.isDoingSelection", selectors), false);
            }
            else if(tile.getTotemState() instanceof StateStartup state) {
                player.displayClientMessage(Component.translatable("totemic.isDoingStartup", state.getCeremony().getDisplayName()), false);
            }
            else if(tile.getTotemState() instanceof StateCeremonyEffect state) {
                player.displayClientMessage(Component.translatable("totemic.isDoingCeremony", state.getCeremony().getDisplayName()), false);
            }
        });
        return InteractionResult.SUCCESS;
    }

    @Override
    public void attack(BlockState state, Level level, BlockPos pos, Player player) {
        if(player.getMainHandItem().getItem() == ModItems.totemic_staff.get()) {
            level.getBlockEntity(pos, ModBlockEntities.totem_base.get())
                    .ifPresent(TotemBaseBlockEntity::resetTotemState);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if(facing == Direction.UP) {
            level.getBlockEntity(currentPos, ModBlockEntities.totem_base.get())
                    .ifPresent(TotemBaseBlockEntity::onPoleChange);
        }
        BlockUtil.scheduleWaterloggedTick(state, currentPos, level);
        return state;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.getBlockEntity(pos, ModBlockEntities.totem_base.get())
                .ifPresent(pole -> pole.setWoodType(TotemPoleItem.getWoodType(stack)));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, BlockUtil.placedInWater(context));
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return level.getBlockEntity(pos, ModBlockEntities.totem_base.get())
                .map(base -> base.getTotemState().getAnalogOutputSignal())
                .orElse(0);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TotemBaseBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return BlockUtil.createTickerHelper(type, ModBlockEntities.totem_base.get(), TotemBaseBlockEntity::tick);
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
        return false;
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
    public MaterialColor getMapColor(BlockState state, BlockGetter level, BlockPos pos, MaterialColor defaultColor) {
        return level.getBlockEntity(pos, ModBlockEntities.totem_base.get())
                .map(base -> base.getWoodType().getWoodColor())
                .orElse(defaultColor);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        var tile = pLevel.getBlockEntity(pPos, ModBlockEntities.totem_base.get());
        var woodType = tile.map(TotemBaseBlockEntity::getWoodType).orElse(ModContent.oak);
        var stack = new ItemStack(this);
        stack.getOrCreateTag().putString(TotemPoleItem.POLE_WOOD_KEY, woodType.getRegistryName().toString());
        return stack;
    }

    @Override
    public void fillItemCategory(CreativeModeTab pTab, NonNullList<ItemStack> pItems) {
        var stack = new ItemStack(this);
        stack.getOrCreateTag().putString(TotemPoleItem.POLE_WOOD_KEY, "totemic:cedar");
        pItems.add(stack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if(pFlag.isAdvanced()) {
            var woodTypeID = Optional.ofNullable(pStack.getTag())
                    .map(tag -> tag.getString(TotemPoleItem.POLE_WOOD_KEY))
                    .filter(str -> !str.isEmpty())
                    .orElse("totemic:oak");
            pTooltip.add(Component.translatable("totemic.woodTypeIdTooltip", woodTypeID).withStyle(ChatFormatting.GRAY));
        }
    }
}
