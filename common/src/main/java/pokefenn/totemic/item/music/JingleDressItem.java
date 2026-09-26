package pokefenn.totemic.item.music;

import java.util.function.Consumer;

import org.jspecify.annotations.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModDataComponents;

public class JingleDressItem extends Item {
    public JingleDressItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        if(slot == EquipmentSlot.LEGS && !level.isClientSide() && owner instanceof Player player && !player.isSpectator() && player.tickCount % 20 == 0) {
            final double chargeFactor = 10.0;
            final int maxSingleCharge = 8;
            final int chargeLimit = 10;

            double velocity = player.getDeltaMovement().length(); // TODO: This had to be changed since x/y/zCloak are no longer available. Test if this works
            if(player.hasEffect(MobEffects.SPEED))
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
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        builder.accept(Component.translatable(getDescriptionId() + ".tooltip"));
    }
}
