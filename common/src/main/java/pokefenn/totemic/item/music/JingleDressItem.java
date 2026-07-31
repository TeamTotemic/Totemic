package pokefenn.totemic.item.music;

import java.util.List;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModDataComponents;
import pokefenn.totemic.init.ModItems;

public class JingleDressItem extends ArmorItem {
    public JingleDressItem(Properties pProperties) {
        super(ModItems.JINGLE_DRESS_MATERIAL, Type.LEGGINGS, pProperties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        final int leggingsSlot = 37;
        if(slotId == leggingsSlot && !level.isClientSide && entity instanceof Player player && !player.isSpectator() && player.tickCount % 20 == 0) {
            final double chargeFactor = 10.0;
            final int maxSingleCharge = 8;
            final int chargeLimit = 10;

            double dx = player.xCloak - player.getX();
            double dy = player.yCloak - player.getY();
            double dz = player.zCloak - player.getZ();
            double velocity = Math.sqrt(dx*dx + dy*dy + dz*dz);
            if(player.hasEffect(MobEffects.MOVEMENT_SPEED))
                velocity *= 1.2;

            int charge = stack.getOrDefault(ModDataComponents.JINGLE_DRESS_CHARGE.get(), 0);
            charge += Mth.clamp((int)(velocity * chargeFactor), 0, maxSingleCharge);
            if(charge >= chargeLimit) {
                TotemicAPI.get().music().playMusic(player, ModContent.jingle_dress.get());
                charge %= chargeLimit;
            }
            stack.set(ModDataComponents.JINGLE_DRESS_CHARGE.get(), charge);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable(getDescriptionId() + ".tooltip"));
    }
}
