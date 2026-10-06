package pokefenn.totemic.neoforge.compat.kubejs;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.script.KubeJSContext;
import dev.latvian.mods.kubejs.script.SourceLine;
import dev.latvian.mods.kubejs.typings.Info;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import pokefenn.totemic.api.music.MusicInstrument;

public class ModifyMusicInstrumentsKubeEvent implements KubeEvent {
    @Info("""
            Modifies the given Music Instrument.
            """)
    public void modify(MusicInstrument instr, Consumer<MusicInstrumentModification> c) {
        c.accept(new MusicInstrumentModification(instr));
    }

    public record MusicInstrumentModification(MusicInstrument instrument) {
        @Deprecated(forRemoval = true)
        public void setItem(KubeJSContext cx, ItemStack item) {
            cx.getConsole().warn("item with modifyMusicInstruments is deprecated, use displayItem instead");
            setDisplayItem(cx, item);
        }

        @Info("Sets the display item stack that is associated with this instrument. This will be displayed in the Totempedia and on the Ceremony HUD.")
        public void setDisplayItem(KubeJSContext cx, ItemStack item) {
            if(!item.isEmpty())
                instrument.setItem(ItemStackTemplate.fromNonEmptyStack(item));
            else {
                // warn because KubeJS silently converts invalid IDs to empty ItemStacks, TODO: Check if KubeJS still does this
                cx.getConsole().warn("item for Music Instrument '" + instrument.getRegistryName() + "' is invalid or empty", SourceLine.of(cx), null, null);
                instrument.setItem((ItemStackTemplate) null);
            }
        }

        @Info("Sets the sound associated with this instrument. May be `null`, in which case no sound will be played.")
        public void setSound(@Nullable SoundEvent sound) {
            if(sound != null)
                instrument.setSound(() -> sound);
            else
                instrument.setSound(null);
        }

        @Info("""
                Returns the default music output when the instrument is played. The instrument's actual music output may differ
                from this value, as the instrument can generally override it.

                Higher values generally make Ceremonies easier to perform.
                """)
        public int getBaseOutput() {
            return instrument.getBaseOutput();
        }

        @Info("""
                Sets the default music output when the instrument is played. The instrument's actual music output may differ
                from this value, as the instrument can generally override it.

                Higher values generally make Ceremonies easier to perform.
                """)
        public void setBaseOutput(int baseOutput) {
            instrument.setBaseOutput(baseOutput);
        }

        @Info("""
                Returns the maximum amount of music that a Totem Base can receive from this instrument before getting saturated.

                These values determine which Ceremonies can be performed at a given point in Totemic's progression, and should
                be modified with care.
                """)
        public int getMusicMaximum() {
            return instrument.getMusicMaximum();
        }

        @Info("""
                Sets the maximum amount of music that a Totem Base can receive from this instrument before getting saturated.

                These values determine which Ceremonies can be performed at a given point in Totemic's progression, and should
                be modified with care.
                """)
        public void setMusicMaximum(int musicMaximum) {
            instrument.setMusicMaximum(musicMaximum);
        }
    }
}
