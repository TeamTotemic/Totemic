package pokefenn.totemic.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.totem.TotemCarving;
import pokefenn.totemic.init.ModContent;

public class CreativeMedicineBagItem extends MedicineBagItem {
    public CreativeMedicineBagItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void tryCharge(ItemStack stack, Level level, long gameTime, BlockPos pos) { }

    @Override
    protected void applyEffects(ItemStack stack, Level level, long gameTime, Entity entity, TotemCarving carving) {
        final int creativeChargeValue = -1;
        for(var effect : carving.getEffects()) {
            if(gameTime % effect.getInterval() == 0)
                effect.medicineBagEffect((Player) entity, stack, creativeChargeValue);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String key;
        if(getCarving(stack) != ModContent.none.get())
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
