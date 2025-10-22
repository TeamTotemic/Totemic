package pokefenn.totemic.compat.kubejs;

import java.util.List;
import java.util.function.Consumer;

import dev.latvian.mods.kubejs.event.EventJS;
import dev.latvian.mods.kubejs.typings.Info;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;

public class ModifyCeremoniesKubeEvent extends EventJS {
    @Info("""
            Modifies the given Ceremony.
            """)
    public void modify(Ceremony ceremony, Consumer<CeremonyModification> c) {
        c.accept(new CeremonyModification(ceremony));
    }

    public record CeremonyModification(Ceremony ceremony) {
        @Info("""
                Returns the amount of music needed to start the ceremony.

                This value determines which Music Instruments are necessary to successfully perform this ceremony, and should
                be modified with care.
                """)
        public int getMusicNeeded() {
            return ceremony.getMusicNeeded();
        }

        @Info("""
                Changes the amount of music needed to start the ceremony.

                This value determines which Music Instruments are necessary to successfully perform this ceremony, and should
                be modified with care.
                """)
        public void setMusicNeeded(int musicNeeded) {
            ceremony.setMusicNeeded(musicNeeded);
        }

        @Info("""
                Returns the maximum time in ticks that the player may take to start the ceremony.

                This value will be adjusted depending on the level's difficulty.
                """)
        public int getMaxStartupTime() {
            return ceremony.getMaxStartupTime();
        }

        @Info("""
                Changes the maximum time in ticks that the player may take to start the ceremony.

                This value will be adjusted depending on the level's difficulty.
                """)
        public void setMaxStartupTime(int maxStartupTime) {
            ceremony.setMaxStartupTime(maxStartupTime);
        }

        @Info("Changes the ceremony's selecting instruments.")
        public void setSelectors(List<MusicInstrument> selectors) {
            ceremony.setSelectors(selectors);
        }
    }
}
