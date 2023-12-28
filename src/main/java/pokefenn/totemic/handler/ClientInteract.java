package pokefenn.totemic.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent.MouseScrollingEvent;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.item.TotemKnifeItem;
import pokefenn.totemic.network.NetworkHandler;
import pokefenn.totemic.network.ServerboundPacketMouseWheel;

public class ClientInteract {
    @SuppressWarnings("resource")
    @SubscribeEvent
    public static void onMouseScroll(MouseScrollingEvent event) {
        if(event.isCanceled())
            return;

        Player player = Minecraft.getInstance().player;
        if(player == null || !player.isShiftKeyDown())
            return;
        ItemStack stack = player.getMainHandItem();
        if(stack.getItem() == ModItems.totem_whittling_knife.get()) {
            boolean direction = (event.getScrollDeltaY() > 0);
            NetworkHandler.channel.sendToServer(new ServerboundPacketMouseWheel(direction));
            player.setItemInHand(InteractionHand.MAIN_HAND, TotemKnifeItem.changeIndex(stack, direction));
            event.setCanceled(true);
        }
    }
}
