package pokefenn.totemic.neoforge.compat.kubejs;

import java.util.List;

import javax.annotation.Nullable;

import dev.latvian.mods.kubejs.level.KubeLevelEvent;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.api.neoforge.event.CeremonyEvent;

public abstract class CeremonyKubeEvent implements KubeLevelEvent {
    protected abstract CeremonyEvent getEvent();

    @Override
    @Info("the level where the Ceremony is performed")
    public Level getLevel() {
        return (Level) getEvent().getLevel();
    }

    @Info("the position of the Totem Base where the Ceremony is performed")
    public BlockPos getPos() {
        return getEvent().getPos();
    }

    @Info("the Ceremony that is being performed")
    public Ceremony getCeremony() {
        return getEvent().getCeremony();
    }

    @Info("the CeremonyInstance of the performed Ceremony")
    public CeremonyInstance getCeremonyInstance() {
        return getEvent().getCeremonyInstance();
    }

    @Info("""
        This event is fired when the required number of instruments for selecting a Ceremony has been played,
        even when the instruments don't match any Ceremony.
        It allows changing the Ceremony that will be selected (if any), and skipping the Ceremony's selection check.

        This events supports a Ceremony as target. To handle the case where no valid Ceremony was selected, omit the
        target and check if `event.ceremony` is null.

        When canceled (or `event.ceremony` is set to null), no Ceremony will be selected.
        """)
    public static class Selection implements KubeLevelEvent {
        private final CeremonyEvent.Selection event;

        public Selection(CeremonyEvent.Selection event) {
            this.event = event;
        }

        @Info("the level where the Ceremony is performed")
        @Override
        public Level getLevel() {
            return (Level) event.getLevel();
        }

        @Info("the position of the Totem Base where the Ceremony is performed")
        public BlockPos getPos() {
            return event.getPos();
        }

        @Info("the Entity who played the last selecting instrument for the Ceremony")
        public Entity getInitiator() {
            return event.getInitiator();
        }

        @Info("the list of selecting instruments")
        public List<MusicInstrument> getSelectors() {
            return event.getSelectors();
        }

        @Info("the Ceremony that is about to be selected. May be null if the selecting instruments don't match any"
                + "Ceremony, or if the value was modified.")
        @Nullable
        public Ceremony getCeremony() {
            return event.getCeremony().orElse(null);
        }

        @Info("Modifies the Ceremony that will be selected. Pass null to select no Ceremony.")
        public void setCeremony(@Nullable Ceremony ceremony) {
            event.setCeremony(ceremony);
        }

        @Info("If true, the Ceremony's selection check (e.g. the Buffalo Dance checking for cows) will be skipped.")
        public boolean getSkipSelectionCheck() {
            return event.getSkipSelectionCheck();
        }

        @Info("When set to true, the Ceremony's selection check (e.g. the Buffalo Dance checking for cows) will be skipped.")
        public void setSkipSelectionCheck(boolean skipSelectionCheck) {
            event.setSkipSelectionCheck(skipSelectionCheck);
        }
    }

    @Info("""
        This event is fired every tick during the Ceremony startup phase.

        When canceled, side effects of the startup (e.g. the damage dealt by the Sun Dance) will not be applied.
        The startup phase can be skipped or aborted entirely by using `event.context.startCeremony()` or
        `event.context.failCeremony()`, respectively.
        """)
    public static class StartupTick extends CeremonyKubeEvent {
        private final CeremonyEvent.StartupTick event;

        public StartupTick(CeremonyEvent.StartupTick event) {
            this.event = event;
        }

        @Override
        protected CeremonyEvent.StartupTick getEvent() {
            return event;
        }

        @Info("a StartupContext providing details about the Ceremony's progress and allowing control over the Ceremony")
        public StartupContext getContext() {
            return event.getContext();
        }
    }

    @Info("""
        This event is fired when the player was not successful in completing the ceremony startup because the time ran out.

        This event is only fired on the server side.
        """)
    public static class StartupFail extends CeremonyKubeEvent {
        private final CeremonyEvent.StartupFail event;

        public StartupFail(CeremonyEvent.StartupFail event) {
            this.event = event;
        }

        @Override
        protected CeremonyEvent.StartupFail getEvent() {
            return event;
        }

        @Info("a StartupContext providing details about the Ceremony's progress and allowing control over the Ceremony")
        public StartupContext getContext() {
            return event.getContext();
        }
    }

    @Info("""
        This event is fired when the player has successfully completed the ceremony startup.

        When canceled, the Ceremony is considered failed and the effect is not started (however, this behavior will probably change in the future).
        This event is only fired on the server side, and it will not fire when the player uses the Creative Ceremony Cheat item (consider using the
        `ceremonyEffectTick` event instead if you need this).
        """)
    public static class StartupSuccess extends CeremonyKubeEvent {
        private final CeremonyEvent.StartupSuccess event;

        public StartupSuccess(CeremonyEvent.StartupSuccess event) {
            this.event = event;
        }

        @Override
        protected CeremonyEvent.StartupSuccess getEvent() {
            return event;
        }

        @Info("a StartupContext providing details about the Ceremony's progress and allowing control over the Ceremony")
        public StartupContext getContext() {
            return event.getContext();
        }
    }

    @Info("""
        This event is fired every tick during the Ceremony effect phase. If the Ceremony effect is instantaneous (i.e. `event.effectTime == 0`),
        it will be fired exactly once.

        When canceled, the Ceremony effect will not be applied. The Ceremony can be ended prematurely by using `event.context.endCeremony()`.
        """)
    public static class EffectTick extends CeremonyKubeEvent {
        private final CeremonyEvent.EffectTick event;

        public EffectTick(CeremonyEvent.EffectTick event) {
            this.event = event;
        }

        @Override
        protected CeremonyEvent.EffectTick getEvent() {
            return event;
        }

        @Info("a CeremonyEffectContext providing details about the Ceremony's progress and allowing control over the Ceremony")
        public CeremonyEffectContext getContext() {
            return event.getContext();
        }

        @Info("the number of ticks that the Ceremony effect will last")
        public int getEffectTime() {
            return event.getEffectTime();
        }

        @Info("""
                Changes the number of ticks that the Ceremony effect will last.

                Keep in mind that changing this to a non-zero value for instantaneous effects may produce unexpected results.
                """)
        public void setEffectTime(int effectTime) {
            event.setEffectTime(effectTime);
        }
    }
}
