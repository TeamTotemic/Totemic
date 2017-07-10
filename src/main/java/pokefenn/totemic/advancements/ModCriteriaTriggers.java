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
        //TODO: Remove this once Forge supports registering your own criteria
        Method registerMethod = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);
        try
        {
            registerMethod.invoke(null, PERFORM_CEREMONY);
        }
        catch(ReflectiveOperationException e)
        {
            throw new RuntimeException(e);
        }
    }
}
