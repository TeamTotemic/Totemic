package pokefenn.totemic.api.ceremony;

import java.util.List;
import java.util.Map;

import pokefenn.totemic.api.music.MusicInstrument;

public interface CeremonyAPI {
    /**
     * The minimum number of music instruments for selecting a ceremony.
     */
    static final int MIN_SELECTORS = 2;
    /**
     * The maximum number of music instruments for selecting a ceremony.
     */
    static final int MAX_SELECTORS = 2;

    /**
     * @return the map which maps a list of selectors to their ceremony.
     */
    Map<List<MusicInstrument>, Ceremony> getSelectorsToCeremonyMap();
}
