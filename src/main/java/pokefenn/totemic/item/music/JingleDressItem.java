package pokefenn.totemic.item.music;

import java.util.EnumMap;
import java.util.List;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
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
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModItems;

public class JingleDressItem extends ArmorItem {
    public static final ArmorMaterial MATERIAL = new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, 1);
                map.put(ArmorItem.Type.LEGGINGS, 1);
                map.put(ArmorItem.Type.CHESTPLATE, 1);
                map.put(ArmorItem.Type.HELMET, 1);
                map.put(ArmorItem.Type.BODY, 1);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(ModItems.buffalo_hide.get()),
            List.of(new ArmorMaterial.Layer(Totemic.resloc("jingle_dress"))),
            0.0F,
            0.0F); //TODO: Register this

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
                TotemicAPI.get().music().playMusic(player, ModContent.jingle_dress.get());
                charge %= chargeLimit;
            }
            stack.getTag().putByte(CHARGE_KEY, charge);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(getDescriptionId() + ".tooltip"));
    }
}
