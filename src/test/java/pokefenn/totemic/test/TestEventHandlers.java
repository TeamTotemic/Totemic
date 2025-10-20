package pokefenn.totemic.test;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.event.CeremonyEvent;

//Some event handlers that print log messages to allow manual testing of Totemic events.
@EventBusSubscriber(modid = TotemicAPI.MOD_ID)
public class TestEventHandlers {
    @SubscribeEvent
    public static void onSelection(CeremonyEvent.Selection event) {
        Totemic.logger.debug("CeremonyEvent.Selection fired: {}" , event.getCeremony());
        //event.setSkipSelectionCheck(true);
        //event.setCeremony(null);
        //event.setCeremony(ModContent.buffalo_dance.get());
    }

    @SubscribeEvent
    public static void onStartupTick(CeremonyEvent.StartupTick event) {
        if(event.getContext().getTime() % 20 == 0)
            Totemic.logger.debug("CeremonyEvent.StartupTick fired (time = {})", event.getContext().getTime());
        //event.setCanceled(true);
        //event.getContext().startCeremony();
        //event.getContext().failCeremony();
    }

    @SubscribeEvent
    public static void onStartupFail(CeremonyEvent.StartupFail event) {
        Totemic.logger.debug("CeremonyEvent.StartupFail fired");
    }

    @SubscribeEvent
    public static void onStartupSuccess(CeremonyEvent.StartupSuccess event) {
        Totemic.logger.debug("CeremonyEvent.StartupSuccess fired");
        //event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onEffectTick(CeremonyEvent.EffectTick event) {
        if(event.getContext().getTime() % 20 == 0)
            Totemic.logger.debug("CeremonyEvent.EffectTick fired (time = {})", event.getContext().getTime());
        /*if(event.getContext().getTime() >= 5*20) { //will stop the effect from applying after 5 seconds
            event.setCanceled(true);
        }*/
        /*if(event.getContext().getTime() >= 10*20) { //will completely end the effect after 10 seconds
            event.getContext().endCeremony();
        }*/
    }
}
