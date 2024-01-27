package pokefenn.totemic.item.music;

import java.util.List;

import javax.annotation.Nullable;

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

    public static final String CHARGE_KEY = "Charge";

    public JingleDressItem(Properties pProperties) {
        super(MATERIAL, Type.LEGGINGS, pProperties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if(!level.isClientSide && !player.isSpectator() && player.tickCount % 20 == 0) {
            final double chargeFactor = 10.0;
            final int maxSingleCharge = 8;
            final int chargeLimit = 10;

            double dx = player.xCloak - player.getX();
            double dy = player.yCloak - player.getY();
            double dz = player.zCloak - player.getZ();
            double velocity = Math.sqrt(dx*dx + dy*dy + dz*dz);
            if(player.hasEffect(MobEffects.MOVEMENT_SPEED))
                velocity *= 1.2;

            var charge = stack.getOrCreateTag().getByte(CHARGE_KEY);
            charge += Mth.clamp((int)(velocity * chargeFactor), 0, maxSingleCharge);
            if(charge >= chargeLimit) {
                TotemicAPI.get().music().playMusic(player, ModContent.jingle_dress);
                charge %= chargeLimit;
            }
            stack.getTag().putByte(CHARGE_KEY, charge);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(getDescriptionId() + ".tooltip"));
    }
}
