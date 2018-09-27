package pokefenn.totemic.totem;

import static pokefenn.totemic.Totemic.logger;

import java.lang.reflect.Field;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.totem.TotemBase;
import pokefenn.totemic.api.totem.TotemEffect;

public class TotemEffectOcelot extends TotemEffect
{
    public TotemEffectOcelot(String name)
    {
        super(name, false, 10);
    }

    private static final Field timeSinceIgnited = ReflectionHelper.findField(EntityCreeper.class, "timeSinceIgnited", "field_70833_d");

    @Override
    public void effect(World world, BlockPos pos, TotemBase totem, int repetition)
    {
        if(world.isRemote)
            return;

        try
        {
            int range = Totemic.api.totemEffect().getDefaultRange(this, totem, repetition);
            for(EntityCreeper entity : TotemicEntityUtil.getEntitiesInRange(EntityCreeper.class, world, pos, range, range))
            {
                int ignited = (Integer) timeSinceIgnited.get(entity);

                if(ignited > 15)
                {
                    timeSinceIgnited.setInt(entity, 0);
                    entity.setCreeperState(-1);
                }
            }
        }
        catch(ReflectiveOperationException e)
        {
            logger.error("Could not perform Ocelot Totem effect", e);
        }
    }

}
