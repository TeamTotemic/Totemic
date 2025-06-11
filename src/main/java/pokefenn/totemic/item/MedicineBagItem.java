package pokefenn.totemic.item;

import java.util.List;
import java.util.Optional;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.TotemicEventHooks;
import pokefenn.totemic.api.totem.MedicineBagEffect;
import pokefenn.totemic.api.totem.PortableTotemCarving;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModDataComponents;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.util.BlockUtil;
import pokefenn.totemic.util.MiscUtil;

@SuppressWarnings("deprecation")
public class MedicineBagItem extends Item {
    public static final int MAX_CHARGE = 4 * 60 * 20;

    public MedicineBagItem(Properties pProperties) {
        super(pProperties);
    }

    public void registerItemProperties() {
        ItemPropertyFunction func = (stack, level, entity, seed) -> isOpen(stack) ? 1.0F : 0.0F;
        var name = Totemic.resloc("open");
        ItemProperties.register(ModItems.medicine_bag.get(), name, func);
        ItemProperties.register(ModItems.creative_medicine_bag.get(), name, func);
    }

    public static Optional<PortableTotemCarving> getCarving(ItemStack stack) {
        return MiscUtil.filterAndCast(Optional.ofNullable(stack.get(ModDataComponents.CARVING))
                .filter(carving -> carving != ModContent.none.get()),
                PortableTotemCarving.class);
    }

    public static List<MedicineBagEffect> getEffects(ItemStack stack) {
        return getCarving(stack)
                .map(PortableTotemCarving::getMedicineBagEffects)
                .orElse(List.of());
    }

    public static int getCharge(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.MEDICINE_BAG_CHARGE, 0);
    }

    public static boolean isOpen(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.OPEN, false);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        level.getProfiler().push("totemic.medicineBag");

        if(!level.isClientSide && level.getGameTime() % 20 == 0)
            tryCharge(stack, level, entity.blockPosition());

        if(isOpen(stack)) {
            int charge = getCharge(stack);
            if(charge > 0) {
                getEffects(stack).forEach(effect -> {
                    int interval = effect.getInterval();
                    if(level.getGameTime() % interval == 0) {
                        var carving = getCarving(stack).get(); //Optional.get is safe since getEffects returned a non-empty list
                        var player = (Player) entity;
                        var eventResult = TotemicEventHooks.get().fireMedicineBagEffectEvent(effect, carving, player, stack, charge, interval);
                        int chargeToDeduct = eventResult.firstInt();
                        if(eventResult.rightBoolean())
                            effect.medicineBagEffect(player, stack, charge);
                        stack.set(ModDataComponents.MEDICINE_BAG_CHARGE, Math.max(charge - chargeToDeduct, 0)); //TODO: This is called multiple times on carvings with multiple effects, which can be problematic especially when they have different intervals
                    }
                });
            }
        }

        level.getProfiler().pop();
    }

    private void tryCharge(ItemStack stack, Level level, BlockPos pos) {
        int charge = getCharge(stack);
        if(charge < MAX_CHARGE) {
            getCarving(stack).ifPresent(carving -> {
                if(BlockUtil.getBlockEntitiesInRange(ModBlockEntities.totem_base.get(), level, pos, 6)
                        .anyMatch(tile -> tile.getTotemState() instanceof StateTotemEffect && tile.hasCarving(carving))) {
                    stack.set(ModDataComponents.MEDICINE_BAG_CHARGE, Math.min(charge + MAX_CHARGE / 12, MAX_CHARGE));
                }
            });
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return toggleOpen(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        var stack = ctx.getItemInHand();
        if(!ctx.isSecondaryUseActive()) {
            var result = toggleOpen(stack);
            return result.getResult();
        }
        else
            return trySetCarving(stack, ctx.getPlayer(), ctx.getLevel(), ctx.getClickedPos(), ctx.getHand());
    }

    private InteractionResultHolder<ItemStack> toggleOpen(ItemStack stack) {
        if(getCarving(stack).isPresent()) {
            stack.update(ModDataComponents.OPEN, false, open -> !open);
            return InteractionResultHolder.success(stack);
        }
        else
            return InteractionResultHolder.fail(stack);
    }

    private InteractionResult trySetCarving(ItemStack stack, Player player, Level level, BlockPos pos, InteractionHand hand) {
        if(level.getBlockEntity(pos) instanceof TotemPoleBlockEntity pole) {
            var carving = pole.getCarving();
            if(carving instanceof PortableTotemCarving) {
                var newStack = stack.copy();
                newStack.set(ModDataComponents.CARVING, carving);
                if(!newStack.is(ModItems.creative_medicine_bag.get()))
                    newStack.set(ModDataComponents.MEDICINE_BAG_CHARGE, 0);
                player.setItemInHand(hand, newStack);
                return InteractionResult.SUCCESS;
            }
            else {
                if(level.isClientSide)
                    player.displayClientMessage(Component.translatable("totemic.medicineBag.notPortable", carving.getDisplayName()), true);
                return InteractionResult.FAIL;
            }
        }
        else
            return InteractionResult.PASS;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(getDescriptionId(),
                getCarving(stack).orElseGet(ModContent.none).getDisplayName());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        String key;
        if(getCarving(stack).isPresent()) {
            if(getCharge(stack) > 0)
                key = isOpen(stack) ? "open" : "closed";
            else
                key = "empty";
        }
        else
            key = "tooltip";
        tooltip.add(Component.translatable("totemic.medicineBag." + key));

        if(flag.isAdvanced())
            tooltip.add(Component.translatable("totemic.medicineBag.charge", getCharge(stack), MAX_CHARGE).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return Math.round(13.0F * getCharge(pStack) / MAX_CHARGE);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        float f = (float) getCharge(pStack) / (float) MAX_CHARGE;
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }
}
