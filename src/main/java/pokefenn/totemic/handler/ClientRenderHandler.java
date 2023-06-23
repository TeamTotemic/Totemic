package pokefenn.totemic.handler;

import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.init.ModItems;

public final class ClientRenderHandler {
    @SubscribeEvent
    public static void handleFieldOfView(FOVModifierEvent event) {
        var player = event.getEntity();
        if(player.isUsingItem() && player.getUseItem().is(ModItems.baykok_bow.get())) {
            int i = player.getTicksUsingItem();
            float f1 = i / 20.0F;
            if (f1 > 1.0F) {
               f1 = 1.0F;
            } else {
               f1 *= f1;
            }

            event.setNewfov(event.getNewfov() * (1.0F - f1 * 0.15F));
        }
    }
}
