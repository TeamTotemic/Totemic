package pokefenn.totemic.handler;

import net.neoforged.bus.api.Event.Result;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModItems;

public class PlayerInteract {
    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if(event.getEntity().isCreative() && event.getUseBlock() != Result.DENY) {
            if(event.getItemStack().getItem() == ModItems.totemic_staff.get()) {
                //Workaround to make left-clicking the Totem Base with a Totemic Staff work in creative mode
                event.getLevel().getBlockEntity(event.getPos(), ModBlockEntities.totem_base.get())
                        .ifPresent(tile -> tile.resetTotemState());
            }
        }
    }
}
