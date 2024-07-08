package pokefenn.totemic.item;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import pokefenn.totemic.TotemicConfig;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.api.totem.TotemWoodType;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModBlocks;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModDataComponents;

public class TotemKnifeItem extends Item {
    public TotemKnifeItem(Properties props) {
        super(props);
    }

    private static MutableComponent getCarvingName(Optional<TotemCarving> carving) {
        return carving.map(TotemCarving::getDisplayName)
                .orElseGet(() -> Component.translatable("block.totemic.totem_base"));
    }

    //an empty Optional represents a Totem Base
    public static Optional<TotemCarving> getCarving(ItemStack stack) {
        return Optional.ofNullable(stack.get(ModDataComponents.CARVING));
    }

    private static List<TotemCarving> totemList; //Lazily created

    public static ItemStack changeIndex(ItemStack stack, boolean direction) {
        var locTotemList = totemList; //avoid reading the field multiple times
        if(locTotemList == null) {
            totemList = locTotemList = TotemicAPI.get().registry().totemCarvings().stream()
                    .filter(e -> e != ModContent.none.get())
                    .toList(); //creates an immutable list, hence thread safe
        }

        var optCarving = getCarving(stack);
        int index = optCarving.isEmpty() ? -1 : locTotemList.indexOf(optCarving.get());

        if(index == -1) {
            index = direction ? 0 : locTotemList.size() - 1;
        }
        else {
            index += direction ? 1 : -1;
            if(index >= locTotemList.size())
                index = -1;
        }

        var newStack = stack.copy();
        if(index == -1)
            newStack.remove(ModDataComponents.CARVING);
        else
            newStack.set(ModDataComponents.CARVING, locTotemList.get(index));
        return newStack;

    }

    @SuppressWarnings("resource")
    @Override
    public InteractionResult useOn(UseOnContext c) {
        var player = c.getPlayer();
        if(player != null && player.isShiftKeyDown()) {
            return InteractionResult.PASS;
        }
        else {
            var state = c.getLevel().getBlockState(c.getClickedPos());
            var woodType = getWoodTypeForLog(state);
            if(woodType.isEmpty())
                return InteractionResult.FAIL;

            var carving = getCarving(c.getItemInHand());
            if(carving.isPresent()) {
                if(isCarvingDisabled(carving.get(), player))
                    return InteractionResult.FAIL;
                var newState = ModBlocks.totem_pole.get().getStateForPlacement(new BlockPlaceContext(c));
                c.getLevel().setBlock(c.getClickedPos(), newState, Block.UPDATE_ALL_IMMEDIATE);
                c.getLevel().getBlockEntity(c.getClickedPos(), ModBlockEntities.totem_pole.get())
                    .ifPresent(pole -> pole.setAppearance(woodType.get(), carving.get()));
            }
            else {
                var newState = ModBlocks.totem_base.get().getStateForPlacement(new BlockPlaceContext(c));
                c.getLevel().setBlock(c.getClickedPos(), newState, Block.UPDATE_ALL_IMMEDIATE);
                c.getLevel().getBlockEntity(c.getClickedPos(), ModBlockEntities.totem_base.get())
                    .ifPresent(base -> base.setWoodType(woodType.get()));
            }

            if(player != null)
                c.getItemInHand().hurtAndBreak(1, player, LivingEntity.getSlotForHand(c.getHand()));
            c.getLevel().levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, c.getClickedPos(), Block.getId(state));

            return InteractionResult.sidedSuccess(c.getLevel().isClientSide);
        }
    }

    private static Optional<TotemWoodType> getWoodTypeForLog(BlockState state) {
        return TotemicAPI.get().registry().woodTypes().stream()
                .filter(wood -> state.is(wood.getLogTag()))
                .findAny()
                .or(() -> { //Fall back to oak if it is an unrecognized log type
                    if(state.is(BlockTags.LOGS_THAT_BURN))
                        return Optional.of(ModContent.oak.get());
                    else
                        return Optional.empty();
                });
    }

    private static boolean isCarvingDisabled(TotemCarving carving, @Nullable Player player) {
        if(TotemicConfig.SERVER.disabledTotemCarvings.get().contains(carving.getRegistryName().toString())) {
            if(player != null && player.level().isClientSide())
                player.sendSystemMessage(Component.translatable("totemic.carvingDisabled", carving.getDisplayName()));
            return true;
        }
        else
            return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if(player.isShiftKeyDown())
            return InteractionResultHolder.success(changeIndex(stack, true));
        else
            return InteractionResultHolder.fail(stack);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(getDescriptionId(stack), getCarvingName(getCarving(stack)));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(getDescriptionId() + ".tooltip"));
    }
}
