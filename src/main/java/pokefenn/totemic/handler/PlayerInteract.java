package pokefenn.totemic.handler;

import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.TotemicConfig;
import pokefenn.totemic.api.event.CeremonyEvent;
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

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void checkCeremonyDisabled(CeremonyEvent.Selection event) {
        event.getCeremony().ifPresent(ceremony -> {
            if(TotemicConfig.SERVER.disabledCeremonies.get().contains(ceremony.getRegistryName().toString())) {
                event.getInitiator().sendSystemMessage(Component.translatable("totemic.ceremonyDisabled", ceremony.getDisplayName()));
                event.setCeremony(null);
            }
        });
    }
}
