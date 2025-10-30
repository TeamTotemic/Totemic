package pokefenn.totemic.compat.kubejs;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import dev.latvian.mods.kubejs.event.StartupEventJS;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.rhino.Context;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import pokefenn.totemic.api.music.MusicInstrument;

public class ModifyMusicInstrumentsKubeEvent extends StartupEventJS {
    @Info("""
            Modifies the given Music Instrument.
            """)
    public void modify(MusicInstrument instr, Consumer<MusicInstrumentModification> c) {
        c.accept(new MusicInstrumentModification(instr));
    }

    public record MusicInstrumentModification(MusicInstrument instrument) {
        @Info("Sets the item stack that is associated with this instrument. This will be displayed in the Totempedia and on the Ceremony HUD.")
        public void setItem(Context cx, ItemStack item) {
            if(item.isEmpty()) // warn because KubeJS silently converts invalid IDs to empty ItemStacks
                ConsoleJS.STARTUP.warn("item for Music Instrument '" + instrument.getRegistryName() + "' is invalid or empty");
            instrument.setItem(item);
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
