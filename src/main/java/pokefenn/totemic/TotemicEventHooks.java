package pokefenn.totemic;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.music.MusicInstrument;

public interface TotemicEventHooks {
    //Ceremony Events
    CeremonySelectionResult fireCeremonySelection(LevelAccessor level, BlockPos pos, Entity initiator, List<MusicInstrument> selectors, Optional<Ceremony> ceremony);
    boolean fireCeremonyStartupTick(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context);
    void fireCeremonyStartupFail(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context);
    boolean fireCeremonyStartupSuccess(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context);
    CeremonyEffectResult fireCeremonyEffectTick(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, CeremonyEffectContext context);

    public record CeremonySelectionResult(Optional<Ceremony> ceremony, boolean skipSelectionCheck) { }
    public record CeremonyEffectResult(boolean callEffect, int effectTime) { }
}
