package pokefenn.totemic.advancements;

import net.minecraft.advancements.CriteriaTriggers;
import pokefenn.totemic.advancements.criterion.CeremonyTrigger;

public final class ModCriteriaTriggers
{
    public static final CeremonyTrigger PERFORM_CEREMONY = new CeremonyTrigger();

    public static void init()
    {
        CriteriaTriggers.register(PERFORM_CEREMONY);
    }
}
