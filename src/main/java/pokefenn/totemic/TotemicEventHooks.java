package pokefenn.totemic;

import java.util.List;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.common.NeoForge;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.event.CeremonyEvent;
import pokefenn.totemic.api.event.TotemEffectEvent;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;

public class TotemicEventHooks {
    //Make the hook methods in this class instance rather static methods, to facilitate future abstraction from NeoForge's event system
    private static final TotemicEventHooks INSTANCE = new TotemicEventHooks();

    public static TotemicEventHooks get() {
        return INSTANCE;
    }

    //Ceremony Events
    /**
     * @return a Pair of the Ceremony to be selected and a Boolean describing whether {@link CeremonyInstance#canSelect} should be called.
     */
    public Pair<Ceremony, Boolean> fireCeremonySelection(LevelAccessor level, BlockPos pos, List<MusicInstrument> selectors, Ceremony ceremony) {
        var event = NeoForge.EVENT_BUS.post(new CeremonyEvent.Selection(level, pos, selectors, ceremony));
        return Pair.of(event.getCeremony(), !event.isCanceled());
    }

    public boolean fireCeremonyStartupTick(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
        return !NeoForge.EVENT_BUS.post(new CeremonyEvent.StartupTick(level, pos, ceremony, instance, context)).isCanceled();
    }

    public void fireCeremonyStartupFail(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
        NeoForge.EVENT_BUS.post(new CeremonyEvent.StartupFail(level, pos, ceremony, instance, context));
    }

    public boolean fireCeremonyStartupSuccess(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
        return !NeoForge.EVENT_BUS.post(new CeremonyEvent.StartupSuccess(level, pos, ceremony, instance, context)).isCanceled();
    }

    public boolean fireCeremonyEffectTick(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, CeremonyEffectContext context) {
        return !NeoForge.EVENT_BUS.post(new CeremonyEvent.EffectTick(level, pos, ceremony, instance, context)).isCanceled();
    }

    //Totem Effect Events
    public boolean fireTotemEffectEvent(LevelAccessor level, BlockPos pos, TotemEffect effect, int repetition, TotemEffectContext context) {
        return !NeoForge.EVENT_BUS.post(new TotemEffectEvent(level, pos, effect, repetition, context)).isCanceled();
    }
}
