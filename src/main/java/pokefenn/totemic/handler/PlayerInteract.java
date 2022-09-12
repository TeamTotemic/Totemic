package pokefenn.totemic.handler;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.init.ModItems;
import pokefenn.totemic.init.ModTileEntities;

public class PlayerInteract {
    @SubscribeEvent
    public static void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        if(event.getEntity().isCreative() && event.getUseBlock() != Result.DENY) {
            if(event.getItemStack().getItem() == ModItems.totemic_staff.get()) {
                //Workaround to make left-clicking the Totem Base with a Totemic Staff work in creative mode
                event.getLevel().getBlockEntity(event.getPos(), ModTileEntities.totem_base.get())
                        .ifPresent(tile -> tile.resetTotemState());
            }
        }
    }
}
