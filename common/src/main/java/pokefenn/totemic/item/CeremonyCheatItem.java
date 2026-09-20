package pokefenn.totemic.item;

import java.util.function.Consumer;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import pokefenn.totemic.block.totem.entity.StateStartup;
import pokefenn.totemic.block.totem.entity.TotemBaseBlockEntity;
import pokefenn.totemic.init.ModBlockEntities;

public class CeremonyCheatItem extends Item {
    public CeremonyCheatItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return context.getLevel().getBlockEntity(context.getClickedPos(), ModBlockEntities.totem_base.get())
                .map(TotemBaseBlockEntity::getTotemState)
                .filter(state -> state instanceof StateStartup)
                .<InteractionResult>map(state -> {
                    ((StateStartup) state).startCeremony();
                    return InteractionResult.SUCCESS;
                })
                .orElse(InteractionResult.FAIL);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        builder.accept(Component.translatable(getDescriptionId() + ".tooltip"));
    }
}
