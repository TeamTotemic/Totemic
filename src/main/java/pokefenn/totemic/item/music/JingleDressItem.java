package pokefenn.totemic.item.music;

import java.util.List;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.common.capabilities.AutoRegisterCapability;
import net.neoforged.neoforge.common.capabilities.Capability;
import net.neoforged.neoforge.common.capabilities.CapabilityManager;
import net.neoforged.neoforge.common.capabilities.CapabilityToken;
import net.neoforged.neoforge.common.util.LazyOptional;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;

public class JingleDressItem extends ArmorItem {
    public static final ArmorMaterial MATERIAL = new ArmorMaterial() {
        @Override
        public float getToughness() { return 0.0F; }

        @Override
        public Ingredient getRepairIngredient() { return Ingredient.of(ModItems.buffalo_hide.get()); }

        @Override
        public String getName() { return "totemic:jingle_dress"; }

        @Override
        public float getKnockbackResistance() { return 0.0F; }

        @Override
        public SoundEvent getEquipSound() { return SoundEvents.ARMOR_EQUIP_LEATHER; }

        @Override
        public int getEnchantmentValue() { return 15; }

        @Override
        public int getDurabilityForType(Type pSlot) { return 5 * 15; }

        @Override
        public int getDefenseForType(Type pSlot) { return 1; }
    };

    //Rather than using the ItemStack's NBT for keeping track of the charge, we attach a Capability to the ItemStack
    private static final Capability<ChargeCounter> CHARGE_COUNTER = CapabilityManager.get(new CapabilityToken<>() {});

    public JingleDressItem(Properties pProperties) {
        super(MATERIAL, Type.LEGGINGS, pProperties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if(!level.isClientSide && !player.isSpectator() && player.tickCount % 20 == 0) {
            stack.getCapability(CHARGE_COUNTER).ifPresent(cc -> {
                final double chargeFactor = 10.0;
                final int maxSingleCharge = 8;
                final int chargeLimit = 10;

                double dx = player.xCloak - player.getX();
                double dy = player.yCloak - player.getY();
                double dz = player.zCloak - player.getZ();
                double velocity = Math.sqrt(dx*dx + dy*dy + dz*dz);
                if(player.hasEffect(MobEffects.MOVEMENT_SPEED))
                    velocity *= 1.2;

                cc.charge += Mth.clamp((int)(velocity * chargeFactor), 0, maxSingleCharge);
                if(cc.charge >= chargeLimit) {
                    TotemicAPI.get().music().playMusic(player, ModContent.jingle_dress);
                    cc.charge %= chargeLimit;
                }
            });
        }
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ChargeCounter();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(getDescriptionId() + ".tooltip"));
    }

    @AutoRegisterCapability
    private static class ChargeCounter implements ICapabilityProvider {
        private LazyOptional<ChargeCounter> holder = LazyOptional.of(() -> this);

        int charge = 0;

        @Override
        public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            return CHARGE_COUNTER.orEmpty(cap, holder);
        }
    }
}
