package pokefenn.totemic.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class CreativeMedicineBagItem extends MedicineBagItem {
    public CreativeMedicineBagItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        final int creativeChargeValue = -1;

        if(isOpen(stack)) {
            getEffects(stack).forEach(effect -> {
                int interval = effect.getInterval();
                if(level.getGameTime() % interval == 0) {
                    effect.medicineBagEffect((Player) entity, stack, creativeChargeValue);
                }
            });
        }
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
