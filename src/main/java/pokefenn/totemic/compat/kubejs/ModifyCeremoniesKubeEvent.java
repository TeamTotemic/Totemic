package pokefenn.totemic.compat.kubejs;

import java.util.function.Consumer;

import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.typings.Info;
import pokefenn.totemic.api.ceremony.Ceremony;

public class ModifyCeremoniesKubeEvent implements KubeEvent {
    @Info("""
            Modifies the given Ceremony.
            """)
    public void modify(Ceremony ceremony, Consumer<CeremonyModification> c) {
        c.accept(new CeremonyModification(ceremony));
    }

    public record CeremonyModification(Ceremony ceremony) {
        @Info("""
                Changes the amount of music needed to start the ceremony.

                This value determines which Music Instruments are necessary to successfully perform this Ceremony, and should
                be modified with care.
                """)
        public void setMusicNeeded(int musicNeeded) {
            ceremony.setMusicNeeded(musicNeeded);
        }

        @Info("""
                Changes the maximum time in ticks that the player may take to start the ceremony in normal difficulty.

                This value will be adjusted depending on the level's difficulty.
                """)
        public void setMaxStartupTime(int maxStartupTime) {
            ceremony.setMaxStartupTime(maxStartupTime);
        }
    }
}
