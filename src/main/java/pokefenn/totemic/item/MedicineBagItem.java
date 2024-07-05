package pokefenn.totemic.item;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.MedicineBagEffect;
import pokefenn.totemic.api.totem.PortableTotemCarving;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.util.BlockUtil;
import pokefenn.totemic.util.MiscUtil;

@SuppressWarnings("deprecation")
public class MedicineBagItem extends Item {
    public static final String TOTEM_TAG = "Totem";
    public static final String CHARGE_TAG = "Charge";
    public static final String OPEN_TAG = "Open";

    private static final Map<ItemStack, Optional<PortableTotemCarving>> carvingCache = Collections.synchronizedMap(new WeakHashMap<>(8));

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
        return carvingCache.computeIfAbsent(stack, st -> MiscUtil.filterAndCast(
                Optional.ofNullable(st.getTag())
                .filter(tag -> tag.contains(TOTEM_TAG, Tag.TAG_STRING))
                .map(tag -> TotemicAPI.get().registry().totemCarvings().getValue(ResourceLocation.tryParse(tag.getString(TOTEM_TAG))))
                .filter(carving -> carving != ModContent.none.get()),
                PortableTotemCarving.class));
    }

    public static List<MedicineBagEffect> getEffects(ItemStack stack) {
        return getCarving(stack)
                .map(PortableTotemCarving::getMedicineBagEffects)
                .orElse(List.of());
    }

    public static int getCharge(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(CHARGE_TAG) : 0;
    }

    public static boolean isOpen(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getBoolean(OPEN_TAG) : false;
    }

    public static int getMaxCharge(ItemStack stack) {
        int unbreaking = stack.getEnchantmentLevel(Enchantments.UNBREAKING);
        return (4 + 2 * unbreaking) * 60 * 20;
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
                        effect.medicineBagEffect((Player) entity, stack, charge);
                        if(!level.isClientSide)
                            stack.getTag().putInt(CHARGE_TAG, Math.max(charge - interval, 0));
                    }
                });
            }
        }

        level.getProfiler().pop();
    }

    private void tryCharge(ItemStack stack, Level level, BlockPos pos) {
        int charge = getCharge(stack);
        int maxCharge = getMaxCharge(stack);
        if(charge < maxCharge) {
            getCarving(stack).ifPresent(carving -> {
                if(BlockUtil.getBlockEntitiesInRange(ModBlockEntities.totem_base.get(), level, pos, 6)
                        .anyMatch(tile -> tile.getTotemState() instanceof StateTotemEffect && tile.hasCarving(carving))) {
                    stack.getTag().putInt(CHARGE_TAG, Math.min(charge + maxCharge / 12, maxCharge));
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
            var tag = stack.getTag();
            tag.putBoolean(OPEN_TAG, !tag.getBoolean(OPEN_TAG));
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
                carvingCache.remove(stack);
                var tag = newStack.getOrCreateTag();
                tag.putString(TOTEM_TAG, carving.getRegistryName().toString());
                if(!newStack.is(ModItems.creative_medicine_bag.get()))
                    tag.putInt(CHARGE_TAG, 0);
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
    public boolean isEnchantable(ItemStack pStack) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 8;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.BLOCK_EFFICIENCY || enchantment == Enchantments.UNBREAKING || super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(getDescriptionId(),
                getCarving(stack).orElseGet(ModContent.none).getDisplayName());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
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
            tooltip.add(Component.translatable("totemic.medicineBag.charge", getCharge(stack), getMaxCharge(stack)).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return Math.round(13.0F * getCharge(pStack) / getMaxCharge(pStack));
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        float f = (float) getCharge(pStack) / (float) getMaxCharge(pStack);
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }
}
