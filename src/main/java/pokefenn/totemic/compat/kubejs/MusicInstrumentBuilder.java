package pokefenn.totemic.compat.kubejs;

import java.util.function.Supplier;

import javax.annotation.Nullable;

import dev.latvian.mods.kubejs.error.KubeRuntimeException;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.script.KubeJSContext;
import dev.latvian.mods.kubejs.script.SourceLine;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.rhino.util.ReturnsSelf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import pokefenn.totemic.api.music.MusicInstrument;

@ReturnsSelf
public class MusicInstrumentBuilder extends BuilderBase<MusicInstrument> {
    public transient int baseOutput = -1;
    public transient int musicMaximum = -1;
    public transient ItemStack displayItem = ItemStack.EMPTY;
    public transient @Nullable Supplier<SoundEvent> sound = null;

    public MusicInstrumentBuilder(ResourceLocation id) {
        super(id);
    }

    @Override
    public MusicInstrument createObject() {
        if(baseOutput < 0)
            throw new KubeRuntimeException("baseOutput not set for Music Instrument '" + id + "'").source(sourceLine);
        if(musicMaximum < 0)
            throw new KubeRuntimeException("musicMaximum not set for Music Instrument '" + id + "'").source(sourceLine);

        return new MusicInstrument(baseOutput, musicMaximum).setItem(displayItem).setSound(sound);
    }

    @Info("""
            Sets the default music output when the instrument is played. The instrument's actual music output may differ
            from this value, as given by the parameters to `TotemicAPI.music().playMusic()`.
            """)
    public MusicInstrumentBuilder baseOutput(int baseOutput) {
        this.baseOutput = baseOutput;
        return this;
    }

    @Info("""
            Sets the maximum amount of music that a Totem Base can receive from this instrument before getting saturated.
            """)
    public MusicInstrumentBuilder musicMaximum(int musicMaximum) {
        this.musicMaximum = musicMaximum;
        return this;
    }

    @Info("""
            Sets the item stack that is associated with this instrument.

            Note that this value is only used for display purposes. In order to have an item actually play music,
            you need to call `TotemicAPI.music().playMusic()` and `playSelector()`, for example from a `use` callback.
            """)
    public MusicInstrumentBuilder displayItem(KubeJSContext cx, ItemStack item) {
        if(item.isEmpty()) // warn because KubeJS silently converts invalid IDs to empty ItemStacks
            cx.getConsole().warn("displayItem for Music Instrument '" + id + "' is invalid or empty", SourceLine.of(cx), null, null);
        this.displayItem = item;
        return this;
    }

    @Info("Sets the sound associated with this instrument. May be `null`, in which case no sound will be played.")
    public MusicInstrumentBuilder sound(@Nullable SoundEvent sound) {
        if(sound != null)
            this.sound = () -> sound;
        else
            this.sound = null;
        return this;
    }

    @Override
    public String getTranslationKeyGroup() {
        return "totemic.instrument";
    }
}
