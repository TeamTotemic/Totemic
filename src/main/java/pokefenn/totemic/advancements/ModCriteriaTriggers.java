package pokefenn.totemic.advancements;

import java.lang.reflect.Method;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import pokefenn.totemic.advancements.criterion.CeremonyTrigger;

public final class ModCriteriaTriggers
{
    public static final CeremonyTrigger PERFORM_CEREMONY = new CeremonyTrigger();

    public static void init()
    {
        register(PERFORM_CEREMONY);
    }

    //TODO: Remove this once Forge supports registering your own criteria
    private static final Method registerMethod = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);

    private static <T extends ICriterionTrigger<?>> T register(T criterion)
    {
        try
        {
            registerMethod.invoke(null, criterion);
        }
        catch(ReflectiveOperationException e)
        {
            throw new RuntimeException(e);
        }
        return criterion;
    }
}
