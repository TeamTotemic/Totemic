package pokefenn.totemic.test;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.event.CeremonyEvent;
import pokefenn.totemic.init.ModContent;

//Some event handlers that print log messages to allow manual testing of Ceremony events.
@EventBusSubscriber(modid = TotemicAPI.MOD_ID)
public class TestCeremonyEventHandlers {
    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onSelection(CeremonyEvent.Selection event) {
        Totemic.logger.debug("CeremonyEvent.Selection fired: " + event.getCeremony());
        if(false) { //modify me in dev
            event.setCanceled(true);
        }
        if(false) {
            event.setCeremony(ModContent.war_dance.get());
        }
    }

    @SubscribeEvent
    public static void onStartupTick(CeremonyEvent.StartupTick event) {
        if(event.getContext().getTime() % 20 == 0)
            Totemic.logger.debug("CeremonyEvent.StartupTick fired (time = " + event.getContext().getTime() + ")");
    }

    @SubscribeEvent
    public static void onStartupFail(CeremonyEvent.StartupFail event) {
        Totemic.logger.debug("CeremonyEvent.StartupFail fired");
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public static void onStartupSuccess(CeremonyEvent.StartupSuccess event) {
        Totemic.logger.debug("CeremonyEvent.StartupSuccess fired");
        if(false) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onEffectTick(CeremonyEvent.EffectTick event) {
        if(event.getContext().getTime() % 20 == 0)
            Totemic.logger.debug("CeremonyEvent.EffectTick fired (time = " + event.getContext().getTime() + ")");
        if(event.getContext().getTime() >= 5*20) { //will stop the effect from applying after 5 seconds
            event.setCanceled(true);
        }
        if(event.getContext().getTime() >= 10*20) { //will completely end the effect after 10 seconds
            event.getContext().endCeremony();
        }
    }
}
