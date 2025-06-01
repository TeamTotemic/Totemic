package pokefenn.totemic.api.event;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.music.MusicInstrument;

public abstract class CeremonyEvent extends Event {
    private final LevelAccessor level;
    private final BlockPos pos;
    private final Ceremony ceremony;
    private final CeremonyInstance instance;

    public CeremonyEvent(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance) {
        this.level = level;
        this.pos = pos;
        this.ceremony = ceremony;
        this.instance = instance;
    }

    /**
     * @return the level where the Ceremony is performed
     */
    public LevelAccessor getLevel() {
        return level;
    }

    /**
     * @return the position of the Totem Base where the Ceremony is performed
     */
    public BlockPos getPos() {
        return pos;
    }

    /**
     * @return the Ceremony that is being performed
     */
    public Ceremony getCeremony() {
        return ceremony;
    }

    /**
     * @return the CeremonyInstance of the performed Ceremony
     */
    public CeremonyInstance getCeremonyInstance() {
        return instance;
    }

    /**
     * This event is fired when the required number of instruments for selecting a Ceremony has been played,
     * even when the instruments don't match any Ceremony.
     * <p>
     * If this event is canceled, no Ceremony will be selected.
     */
    public static class Selection extends Event implements ICancellableEvent {
        private final LevelAccessor level;
        private final BlockPos pos;
        private final List<MusicInstrument> selectors;
        private @Nullable Ceremony ceremony;

        public Selection(LevelAccessor level, BlockPos pos, List<MusicInstrument> selectors, @Nullable Ceremony ceremony) {
            this.level = level;
            this.pos = pos;
            this.selectors = selectors;
            this.ceremony = ceremony;
        }

        /**
         * @return the level where the Ceremony is performed
         */
        public LevelAccessor getLevel() {
            return level;
        }

        /**
         * @return the position of the Totem Base where the Ceremony is performed
         */
        public BlockPos getPos() {
            return pos;
        }

        /**
         * @return the list of selecting instruments
         */
        public List<MusicInstrument> getSelectors() {
            return Collections.unmodifiableList(selectors);
        }

        /**
         * Returns the Ceremony that is about to be selected. May be null if the selecting instruments don't match any
         * Ceremony, or if the value was modified using {@link #setCeremony(Ceremony)}.
         */
        @Nullable
        public Ceremony getCeremony() {
            return ceremony;
        }

        /**
         * Modifies the Ceremony that will be selected.
         */
        public void setCeremony(@Nullable Ceremony ceremony) {
            this.ceremony = ceremony;
        }
    }

    /**
     * This event is fired every tick during the Ceremony startup phase.
     * <p>
     * This event is not cancellable, but the startup phase can be cancelled or skipped using the {@link #getContext()} method.
     */
    public static class StartupTick extends CeremonyEvent {
        private final StartupContext context;

        public StartupTick(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
            super(level, pos, ceremony, instance);
            this.context = context;
        }

        /**
         * @return a StartupContext providing details about the Ceremony's progress and allowing control over the Ceremony
         */
        public StartupContext getContext() {
            return context;
        }
    }
}
