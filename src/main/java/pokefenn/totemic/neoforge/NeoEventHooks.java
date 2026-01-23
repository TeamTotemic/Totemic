package pokefenn.totemic.neoforge;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.common.NeoForge;
import pokefenn.totemic.TotemicEventHooks;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.event.CeremonyEvent;
import pokefenn.totemic.api.music.MusicInstrument;

public class NeoEventHooks implements TotemicEventHooks {
    @Override
    public CeremonySelectionResult fireCeremonySelection(LevelAccessor level, BlockPos pos, Entity initiator, List<MusicInstrument> selectors, Optional<Ceremony> ceremony) {
        var event = NeoForge.EVENT_BUS.post(new CeremonyEvent.Selection(level, pos, initiator, selectors, ceremony));
        return new CeremonySelectionResult(event.getCeremony(), event.getSkipSelectionCheck());
    }

    @Override
    public boolean fireCeremonyStartupTick(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
        return !NeoForge.EVENT_BUS.post(new CeremonyEvent.StartupTick(level, pos, ceremony, instance, context)).isCanceled();
    }

    @Override
    public void fireCeremonyStartupFail(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
        NeoForge.EVENT_BUS.post(new CeremonyEvent.StartupFail(level, pos, ceremony, instance, context));
    }

    @Override
    public boolean fireCeremonyStartupSuccess(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
        return !NeoForge.EVENT_BUS.post(new CeremonyEvent.StartupSuccess(level, pos, ceremony, instance, context)).isCanceled();
    }

    @Override
    public CeremonyEffectResult fireCeremonyEffectTick(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, CeremonyEffectContext context) {
        var event = NeoForge.EVENT_BUS.post(new CeremonyEvent.EffectTick(level, pos, ceremony, instance, context));
        return new CeremonyEffectResult(!event.isCanceled(), event.getEffectTime());
    }
}
