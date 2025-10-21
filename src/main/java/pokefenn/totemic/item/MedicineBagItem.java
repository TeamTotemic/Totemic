package pokefenn.totemic.item;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import pokefenn.totemic.TotemicConfig;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.block.totem.entity.StateTotemEffect;
import pokefenn.totemic.block.totem.entity.TotemPoleBlockEntity;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.util.BlockUtil;

public class MedicineBagItem extends Item {
    public static final String TOTEM_TAG = "Totem";
    public static final String CHARGE_TAG = "Charge";
    public static final String OPEN_TAG = "Open";

    public static final int MAX_CHARGE = 4 * 60 * 20;

    private static final LoadingCache<ItemStack, TotemCarving> carvingCache = CacheBuilder.newBuilder()
            .weakKeys()
            .build(CacheLoader.from(stack ->
                Optional.ofNullable(stack.getTag())
                    .filter(tag -> tag.contains(TOTEM_TAG, Tag.TAG_STRING))
                    .map(tag -> TotemicAPI.get().registry().totemCarvings().getValue(ResourceLocation.tryParse(tag.getString(TOTEM_TAG))))
                    .orElseGet(ModContent.none)
            ));

    public MedicineBagItem(Properties pProperties) {
        super(pProperties);
    }

    @SuppressWarnings("deprecation")
    public void registerItemProperties() {
        ItemPropertyFunction func = (stack, level, entity, seed) -> isOpen(stack) ? 1.0F : 0.0F;
        var name = Totemic.resloc("open");
        ItemProperties.register(ModItems.medicine_bag.get(), name, func);
        ItemProperties.register(ModItems.creative_medicine_bag.get(), name, func);
    }

    public static TotemCarving getCarving(ItemStack stack) {
        return carvingCache.getUnchecked(stack);
    }

    public static int getCharge(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt(CHARGE_TAG) : 0;
    }

    public static boolean isOpen(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getBoolean(OPEN_TAG) : false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        level.getProfiler().push("totemic.medicineBag");

        long gameTime = level.getGameTime();
        tryCharge(stack, level, gameTime, entity.blockPosition());
        if(isOpen(stack)) {
            var carving = getCarving(stack);
            if(carving != ModContent.none.get())
                applyEffects(stack, level, gameTime, entity, carving);
        }

        level.getProfiler().pop();
    }

    protected void tryCharge(ItemStack stack, Level level, long gameTime, BlockPos pos) {
        if(!level.isClientSide && gameTime % 20 == 0) {
            int charge = getCharge(stack);
            if(charge < MAX_CHARGE) {
                var carving = getCarving(stack);
                if(carving != ModContent.none.get()
                        && BlockUtil.getBlockEntitiesInRange(ModBlockEntities.totem_base.get(), level, pos, 6)
                        .anyMatch(tile -> tile.getTotemState() instanceof StateTotemEffect && tile.hasCarving(carving))) {
                    int chargeAmount = MAX_CHARGE / 12;
                    stack.getTag().putInt(CHARGE_TAG, Math.min(charge + chargeAmount, MAX_CHARGE));
                }
            }
        }
    }

    protected void applyEffects(ItemStack stack, Level level, long gameTime, Entity entity, TotemCarving carving) {
        int charge = getCharge(stack);
        if(charge > 0) {
            for(var effect : carving.getEffects()) {
                if(gameTime % effect.getInterval() == 0)
                    effect.medicineBagEffect((Player) entity, stack, charge);
            }
            // Drain the charge independently of the effects' intervals
            if(!level.isClientSide && gameTime % TotemCarving.MEDICINE_BAG_DRAIN_INTERVAL == 0)
                stack.getTag().putInt(CHARGE_TAG, Math.max(charge - carving.getMedicineBagDrain(), 0));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return toggleOpen(pPlayer.getItemInHand(pUsedHand), pLevel, pPlayer);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        var stack = ctx.getItemInHand();
        if(!ctx.isSecondaryUseActive())
            return toggleOpen(stack, ctx.getLevel(), ctx.getPlayer()).getResult();
        else
            return trySetCarving(stack, ctx.getPlayer(), ctx.getLevel(), ctx.getClickedPos(), ctx.getHand());
    }

    private InteractionResultHolder<ItemStack> toggleOpen(ItemStack stack, Level level, Player player) {
        if(getCarving(stack) != ModContent.none.get()) {
            var tag = stack.getTag();
            tag.putBoolean(OPEN_TAG, !tag.getBoolean(OPEN_TAG));
            level.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1.0F, 1.0F, false);
            return InteractionResultHolder.success(stack);
        }
        else
            return InteractionResultHolder.fail(stack);
    }

    private InteractionResult trySetCarving(ItemStack stack, Player player, Level level, BlockPos pos, InteractionHand hand) {
        if(level.getBlockEntity(pos) instanceof TotemPoleBlockEntity pole) {
            var carving = pole.getCarving();
            if(TotemicConfig.SERVER.medicineBagBlacklist.get().contains(carving.getRegistryName().toString())) {
                player.displayClientMessage(Component.translatable("totemic.medicineBag.blacklisted", carving.getDisplayName()), true);
                return InteractionResult.FAIL;
            }
            if(!carving.supportsMedicineBag()) {
                if(level.isClientSide)
                    player.displayClientMessage(Component.translatable("totemic.medicineBag.notPortable", carving.getDisplayName()), true);
                return InteractionResult.FAIL;
            }

            var newStack = stack.copy();
            carvingCache.invalidate(stack);
            var tag = newStack.getOrCreateTag();
            tag.putString(TOTEM_TAG, carving.getRegistryName().toString());
            if(!newStack.is(ModItems.creative_medicine_bag.get()))
                tag.putInt(CHARGE_TAG, 0);
            player.setItemInHand(hand, newStack);
            level.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1.0F, 1.0F, false);
            return InteractionResult.SUCCESS;
        }
        else
            return InteractionResult.PASS;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(getDescriptionId(),
                getCarving(stack).getDisplayName());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String key;
        if(getCarving(stack) != ModContent.none.get()) {
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
        return Math.round((float) Item.MAX_BAR_WIDTH * getCharge(pStack) / MAX_CHARGE);
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        float f = (float) getCharge(pStack) / MAX_CHARGE;
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }
}
