package pokefenn.totemic.advancements;

import java.util.function.BiConsumer;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.resources.Identifier;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.advancements.criterion.CeremonyTrigger;

public final class ModCriteriaTriggers {
    public static final CeremonyTrigger PERFORM_CEREMONY = new CeremonyTrigger();

    public static void register(BiConsumer<Identifier, CriterionTrigger<?>> registry) {
        registry.accept(Totemic.resloc("performed_ceremony"), PERFORM_CEREMONY);
    }
}
