package pokefenn.totemic.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import pokefenn.totemic.init.ModItems;

public final class ClientRenderHandler {
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
