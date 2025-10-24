package pokefenn.totemic.compat.kubejs;

import java.util.Objects;

import dev.latvian.mods.kubejs.error.KubeRuntimeException;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.ReturnsSelf;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.music.MusicInstrument;

@ReturnsSelf
public class CeremonyBuilder extends BuilderBase<Ceremony> {
    public transient int musicNeeded = -1;
    public transient int maxStartupTime = -1;
    public transient MusicInstrument selector1, selector2;
    public transient EffectCallback effect;
    public transient int effectDuration = 0;

    public CeremonyBuilder(ResourceLocation id) {
        super(id);
    }

    @Override
    public Ceremony createObject() {
        if(musicNeeded < 0)
            throw new KubeRuntimeException("musicNeeded not set for Ceremony '" + id + "'").source(sourceLine);
        if(maxStartupTime < 0)
            throw new KubeRuntimeException("maxStartupTime not set for Ceremony '" + id + "'").source(sourceLine);
        if(selector1 == null || selector2 == null)
            throw new KubeRuntimeException("selectors invalid or not set for Ceremony '" + id + "'").source(sourceLine);
        if(effect == null)
            throw new KubeRuntimeException("effect not set for Ceremony '" + id + "'").source(sourceLine);

        var instance = new SimpleCeremonyInstance(effect, effectDuration);
        return new Ceremony(musicNeeded, maxStartupTime, () -> instance, selector1, selector2);
    }

    @Info("Sets the amount of music needed to start the ceremony.")
    public CeremonyBuilder musicNeeded(int music) {
        this.musicNeeded = music;
        return this;
    }

    @Info("""
            Sets the maximum time in ticks that the player may take to start the ceremony.
            This value will be adjusted depending on the level's difficulty.
            """)
    public CeremonyBuilder maxStartupTime(int time) {
        this.maxStartupTime = time;
        return this;
    }

    @Info("Sets the Ceremony's selecting instruments.")
    public CeremonyBuilder selectors(MusicInstrument selector1, MusicInstrument selector2) {
        this.selector1 = Objects.requireNonNull(selector1);
        this.selector2 = Objects.requireNonNull(selector2);
        return this;
    }

    @Info("Sets the Ceremony's effect.")
    public CeremonyBuilder effect(EffectCallback effect) {
        this.effect = Objects.requireNonNull(effect);
        return this;
    }

    @Info("""
            Sets how many ticks the Ceremony effect will last.

            If this is 0 (the default), the effect callback will be called exactly once on the server and client.
            """)
    public CeremonyBuilder effectDuration(int duration) {
        this.effectDuration = duration;
        return this;
    }

    @Override
    public String getTranslationKeyGroup() {
        return "totemic.ceremony";
    }

    @FunctionalInterface
    public interface EffectCallback {
        void effect(Level level, BlockPos pos, CeremonyEffectContext context);
    }

    private record SimpleCeremonyInstance(EffectCallback callback, int duration) implements CeremonyInstance {
        @Override
        public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
            callback.effect(level, pos, context);
        }

        @Override
        public int getEffectTime() {
            return duration;
        }
    }
}
