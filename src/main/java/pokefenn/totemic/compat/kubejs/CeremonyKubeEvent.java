package pokefenn.totemic.compat.kubejs;

import java.util.List;

import javax.annotation.Nullable;

import dev.latvian.mods.kubejs.level.KubeLevelEvent;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.event.CeremonyEvent;
import pokefenn.totemic.api.music.MusicInstrument;

public abstract class CeremonyKubeEvent implements KubeLevelEvent {
    @Info("""
        This event is fired when the required number of instruments for selecting a Ceremony has been played,
        even when the instruments don't match any Ceremony.
        It allows changing the Ceremony that will be selected (if any), and skipping the Ceremony's selection check.
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

        @Info("the list of selecting instruments")
        public List<MusicInstrument> getSelectors() {
            return event.getSelectors();
        }

        @Info("the Ceremony that is about to be selected. May be null if the selecting instruments don't match any"
                + "Ceremony, or if the value was modified.")
        @Nullable
        public Ceremony getCeremony() {
            return event.getCeremony();
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
}
