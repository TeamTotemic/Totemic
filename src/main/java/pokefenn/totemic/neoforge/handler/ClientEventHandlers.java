package pokefenn.totemic.neoforge.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.InputEvent.MouseScrollingEvent;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.item.TotemKnifeItem;
import pokefenn.totemic.network.ServerboundPacketMouseWheel;

public final class ClientEventHandlers {
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
            Totemic.platform().sendPacketToServer(new ServerboundPacketMouseWheel(direction));
            player.setItemInHand(InteractionHand.MAIN_HAND, TotemKnifeItem.changeIndex(stack, direction));
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void handleFieldOfView(ComputeFovModifierEvent event) {
        var player = event.getPlayer();
        if(player.isUsingItem() && player.getUseItem().is(ModItems.baykok_bow.get())) {
            int i = player.getTicksUsingItem();
            float f1 = i / 20.0F;
            if (f1 > 1.0F) {
               f1 = 1.0F;
            } else {
               f1 *= f1;
            }

            event.setNewFovModifier(event.getNewFovModifier() * (1.0F - f1 * 0.15F));
        }
    }
}
