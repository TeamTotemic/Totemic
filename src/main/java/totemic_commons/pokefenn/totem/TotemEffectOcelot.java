package totemic_commons.pokefenn.totem;

import static totemic_commons.pokefenn.Totemic.logger;

import java.lang.reflect.Field;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import totemic_commons.pokefenn.api.totem.TotemBase;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;

public class TotemEffectOcelot extends TotemEffect
{
    public TotemEffectOcelot(String name)
    {
        super(name, false);
    }

    private static final Field timeSinceIgnited = ReflectionHelper.findField(EntityCreeper.class, "timeSinceIgnited", "field_70833_d", "bq");

    @Override
    public void effect(World world, BlockPos pos, TotemBase totem, int repetition)
    {
        if(world.isRemote)
            return;

        try
        {
            for(EntityCreeper entity : EntityUtil.getEntitiesInRange(EntityCreeper.class, world, pos, 6, 6))
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
