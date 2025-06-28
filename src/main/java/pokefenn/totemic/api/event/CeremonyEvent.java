package pokefenn.totemic.api.event;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.music.MusicInstrument;

/**
 * Various events that are fired during different parts of a Ceremony.
 */
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
     * It allows changing the Ceremony that will be selected.
     * <p>
     * When canceled, the Ceremony's own check of whether it can be selected (e.g. the Buffalo Dance checking whether
     * cows are nearby) will be skipped. If you want to prevent selecting a Ceremony, use {@code setCeremony(null)}
     * instead.
     */
    public static class Selection extends Event implements ICancellableEvent {
        //not a subclass of CeremonyEvent since this is the only one where Ceremony is mutable and there's no CeremonyInstance
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
         * Modifies the Ceremony that will be selected, if any.
         */
        public void setCeremony(@Nullable Ceremony ceremony) {
            this.ceremony = ceremony;
        }
    }

    /**
     * This event is fired every tick during the Ceremony startup phase.
     * <p>
     * When canceled, side effects of the startup (e.g. the damage dealt by the Sun Dance) will not be applied.
     * The startup phase can be skipped or aborted entirely by using {@link #getContext()}.
     */
    public static class StartupTick extends CeremonyEvent implements ICancellableEvent {
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

    /**
     * This event is fired when the player was not successful in completing the ceremony startup because the time ran out.
     * <p>
     * This event is only fired on the server side.
     */
    public static class StartupFail extends CeremonyEvent {
        private final StartupContext context;

        public StartupFail(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
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

    /**
     * This event is fired when the player has successfully completed the ceremony startup.
     * <p>
     * When canceled, the Ceremony is considered failed and the effect is not started (however, this behavior will probably change in the future).
     * This event is only fired on the server side, and it will not fire when the player uses the Creative Ceremony Cheat item.
     */
    public static class StartupSuccess extends CeremonyEvent implements ICancellableEvent {
        private final StartupContext context;

        public StartupSuccess(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, StartupContext context) {
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

    /**
     * This event is fired every tick during the Ceremony effect phase. Will only be fired once if the Ceremony effect is instantaneous
     * (i.e. {@link CeremonyInstance#getEffectTime()} == 0).
     * <p>
     * When canceled, {@link CeremonyInstance#effect} will not be called.
     */
    public static class EffectTick extends CeremonyEvent implements ICancellableEvent {
        private final CeremonyEffectContext context;

        public EffectTick(LevelAccessor level, BlockPos pos, Ceremony ceremony, CeremonyInstance instance, CeremonyEffectContext context) {
            super(level, pos, ceremony, instance);
            this.context = context;
        }

        /**
         * @return a CeremonyEffectContext providing details about the Ceremony's progress and allowing control over the Ceremony
         */
        public CeremonyEffectContext getContext() {
            return context;
        }
    }
}
