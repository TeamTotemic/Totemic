package pokefenn.totemic.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import pokefenn.totemic.TotemicEventHooks;

public class CreativeMedicineBagItem extends MedicineBagItem {
    public CreativeMedicineBagItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        level.getProfiler().push("totemic.medicineBag");

        final int creativeChargeValue = -1;
        if(isOpen(stack)) {
            getEffects(stack).forEach(effect -> {
                int interval = effect.getInterval();
                if(level.getGameTime() % interval == 0) {
                    var carving = getCarving(stack).get(); //Optional.get is safe since getEffects returned a non-empty list
                    var player = (Player) entity;
                    var eventResult = TotemicEventHooks.get().fireMedicineBagEffectEvent(effect, carving, player, stack, creativeChargeValue, 0);
                    if(eventResult.secondBoolean())
                        effect.medicineBagEffect(player, stack, creativeChargeValue);
                }
            });
        }

        level.getProfiler().pop();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String key;
        if(getCarving(stack).isPresent())
            key = isOpen(stack) ? "open" : "closed";
        else
            key = "tooltip";
        tooltip.add(Component.translatable("totemic.medicineBag." + key));
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return false;
    }
}
