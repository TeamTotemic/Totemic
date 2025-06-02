package pokefenn.totemic;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.common.NeoForge;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.event.CeremonyEvent;
import pokefenn.totemic.api.music.MusicInstrument;

public class TotemicEventHooks {
    //Make the hook methods in this class instance rather static methods, to facilitate future abstraction from NeoForge's event system
    private static final TotemicEventHooks INSTANCE = new TotemicEventHooks();

    public static TotemicEventHooks get() {
        return INSTANCE;
    }

    public Ceremony fireCeremonySelection(LevelAccessor level, BlockPos pos, List<MusicInstrument> selectors, Ceremony ceremony) {
        var event = new CeremonyEvent.Selection(level, pos, selectors, ceremony);
        return NeoForge.EVENT_BUS.post(event).isCanceled() ? null : event.getCeremony();
    }

    public void fireCeremonyStartupTick(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
        NeoForge.EVENT_BUS.post(new CeremonyEvent.StartupTick(level, pos, ceremony, instance, context));
    }

    public void fireCeremonyStartupFail(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
        NeoForge.EVENT_BUS.post(new CeremonyEvent.StartupFail(level, pos, ceremony, instance, context));
    }
}
