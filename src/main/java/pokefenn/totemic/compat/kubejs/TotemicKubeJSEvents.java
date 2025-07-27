package pokefenn.totemic.compat.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

public class TotemicKubeJSEvents {
    public static final EventGroup GROUP = EventGroup.of("TotemicEvents");

    public static final EventHandler totemEffect = GROUP.common("totemEffect", () -> TotemEffectKubeEvent.class).hasResult();
}
