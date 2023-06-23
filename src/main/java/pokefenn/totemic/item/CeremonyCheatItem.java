package pokefenn.totemic.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import pokefenn.totemic.block.totem.entity.StateStartup;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.init.ModBlockEntities;

public class CeremonyCheatItem extends Item {
    public CeremonyCheatItem(Properties props) {
        super(props);
    }

    @SuppressWarnings("resource")
    @Override
    public InteractionResult useOn(UseOnContext context) {
        return context.getLevel().getBlockEntity(context.getClickedPos(), ModBlockEntities.totem_base.get())
                .map(TotemBaseBlockEntity::getTotemState)
                .filter(state -> state instanceof StateStartup)
                .map(state -> {
                    ((StateStartup) state).startCeremony();
                    return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
                })
                .orElse(InteractionResult.FAIL);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent(getDescriptionId() + ".tooltip"));
    }
}
