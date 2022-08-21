package pokefenn.totemic.apiimpl.ceremony;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyAPI;
import pokefenn.totemic.api.music.MusicInstrument;

public enum CeremonyAPIImpl implements CeremonyAPI {
    INSTANCE;

    private Map<List<MusicInstrument>, Ceremony> selectorsToCeremonyMap = null;

    public Map<List<MusicInstrument>, Ceremony> getSelectorsToCeremonyMap() {
        return selectorsToCeremonyMap;
    }

    public void computeSelectorsToCeremonyMap() {
        //This will throw an exception if two different Ceremonies happen to have the same selectors.
        //Note that this check is not sufficient if MIN_SELECTORS != MAX_SELECTORS. In this case, we would have
        //to check for prefix-freeness. So we assume MIN_SELECTORS == MAX_SELECTORS here.
        selectorsToCeremonyMap = TotemicAPI.get().registry().ceremonies().values().stream()
                .collect(Collectors.toUnmodifiableMap(Ceremony::getSelectors, Function.identity()));
    }
}
