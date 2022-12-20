package pokefenn.totemic.item;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

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
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.MedicineBagEffect;
import pokefenn.totemic.api.totem.PortableTotemCarving;
import pokefenn.totemic.block.totem.TotemPoleBlock;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.util.BlockUtil;
import pokefenn.totemic.util.MiscUtil;

public class MedicineBagItem extends Item {
    public static final String TOTEM_TAG = "Totem";
    public static final String CHARGE_TAG = "Charge";
    public static final String OPEN_TAG = "Open";

    public MedicineBagItem(Properties pProperties) {
        super(pProperties);
    }

    public static Optional<PortableTotemCarving> getCarving(ItemStack stack) {
        return MiscUtil.filterAndCast(
                Optional.ofNullable(stack.getTag())
                .filter(tag -> tag.contains(TOTEM_TAG, Tag.TAG_STRING))
                .flatMap(tag -> TotemicAPI.get().registry().totemCarvings().getOptional(new ResourceLocation(tag.getString(TOTEM_TAG)))),
                PortableTotemCarving.class);
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
            return trySetCarving(stack, ctx.getPlayer(), ctx.getLevel(), ctx.getClickedPos());
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

    private InteractionResult trySetCarving(ItemStack stack, Player player, Level level, BlockPos pos) {
        if(level.getBlockState(pos).getBlock() instanceof TotemPoleBlock block) {
            var carving = block.carving;
            if(carving instanceof PortableTotemCarving) {
                var tag = stack.getOrCreateTag();
                tag.putString(TOTEM_TAG, carving.getRegistryName().toString());
                tag.putInt(CHARGE_TAG, 0);
                return InteractionResult.SUCCESS;
            }
            else {
                if(level.isClientSide)
                    player.displayClientMessage(Component.translatable("totemic.effectNotPortable", carving.getDisplayName()), true);
                return InteractionResult.FAIL;
            }
        }
        else
            return InteractionResult.PASS;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(getDescriptionId(),
                getCarving(stack).orElse((PortableTotemCarving) ModContent.none).getDisplayName());
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
        tooltip.add(Component.translatable(getDescriptionId() + "." + key));

        if(flag.isAdvanced())
            tooltip.add(Component.translatable(getDescriptionId() + ".charge", getCharge(stack), getMaxCharge(stack)));
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