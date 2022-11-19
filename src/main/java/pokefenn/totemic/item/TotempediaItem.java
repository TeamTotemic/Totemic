package pokefenn.totemic.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.music.MusicInstrument;

public class TotempediaItem extends Item {
    public TotempediaItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if(pLevel.isClientSide) {
            //TODO: This is temporary until the Totempedia is implemented, probably using a 3rd party API
            pPlayer.displayClientMessage(Component.translatable("totemic.listOfCeremonies").withStyle(ChatFormatting.YELLOW), false);
            for(var cer: TotemicAPI.get().registry().ceremonies()) {
                var selectors = cer.getSelectors().stream()
                        .map(MusicInstrument::getDisplayName)
                        .reduce((c1, c2) -> c1.append(", ").append(c2))
                        .get();
                var text = Component.empty()
                        .append(cer.getDisplayName().append(": ").withStyle(ChatFormatting.AQUA))
                        .append(selectors.append(": ").withStyle(ChatFormatting.GRAY))
                        .append(Component.translatable(cer.getDescriptionId() + ".description"));
                pPlayer.displayClientMessage(text, false);
            }
        }
        return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable(getDescriptionId() + ".tooltip"));
    }
}
