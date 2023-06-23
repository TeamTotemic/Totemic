package pokefenn.totemic.handler;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.init.ModBlockEntities;
import pokefenn.totemic.init.ModItems;

public class PlayerInteract {
    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if(event.getPlayer().isCreative() && event.getUseBlock() != Result.DENY) {
            if(event.getItemStack().getItem() == ModItems.totemic_staff.get()) {
                //Workaround to make left-clicking the Totem Base with a Totemic Staff work in creative mode
                event.getWorld().getBlockEntity(event.getPos(), ModBlockEntities.totem_base.get())
                        .ifPresent(tile -> tile.resetTotemState());
            }
        }
    }
}
