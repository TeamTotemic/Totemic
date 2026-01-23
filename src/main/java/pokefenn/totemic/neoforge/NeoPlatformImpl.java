package pokefenn.totemic.neoforge;

import pokefenn.totemic.PlatformAbstractions;
import pokefenn.totemic.TotemicEventHooks;

public class NeoPlatformImpl implements PlatformAbstractions {
    private final TotemicEventHooks eventHooks = new NeoEventHooks();

    @Override
    public TotemicEventHooks events() {
        return eventHooks;
    }
}
