package pokefenn.totemic.advancements;

import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.RegisterEvent;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.advancements.criterion.CeremonyTrigger;

public final class ModCriteriaTriggers {
    public static final CeremonyTrigger PERFORM_CEREMONY = new CeremonyTrigger();

    public static void init(RegisterEvent event) {
        event.register(Registries.TRIGGER_TYPE, Totemic.resloc("performed_ceremony"), () -> PERFORM_CEREMONY);
    }
}
