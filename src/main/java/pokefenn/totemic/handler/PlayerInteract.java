package pokefenn.totemic.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.MouseScrollEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.item.TotemKnifeItem;
import pokefenn.totemic.network.NetworkHandler;
import pokefenn.totemic.network.PacketMouseWheel;

public class PlayerInteract {
    @SuppressWarnings("resource")
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onMouseScroll(MouseScrollEvent event) {
        if(event.isCanceled())
            return;

        Player player = Minecraft.getInstance().player;
        ItemStack stack = player.getMainHandItem();
        if(player.isShiftKeyDown() && stack.getItem() == ModItems.totem_whittling_knife.get()) {
            boolean direction = (event.getScrollDelta() > 0);
            NetworkHandler.channel.sendToServer(new PacketMouseWheel(direction));
            player.setItemInHand(InteractionHand.MAIN_HAND, TotemKnifeItem.changeIndex(stack, direction));
            event.setCanceled(true);
        }
    }
}
