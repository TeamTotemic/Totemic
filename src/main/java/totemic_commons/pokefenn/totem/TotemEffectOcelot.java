package totemic_commons.pokefenn.totem;

import static totemic_commons.pokefenn.Totemic.logger;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import totemic_commons.pokefenn.api.totem.TotemBase;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 27/01/14
 * Time: 14:27
 */
public class TotemEffectOcelot extends TotemEffect
{
    public TotemEffectOcelot(String modid, String baseName, int horizontal, int vertical)
    {
        super(modid, baseName, horizontal, vertical);
    }

    private static final Field timeSinceIgnited = ReflectionHelper.findField(EntityCreeper.class, "timeSinceIgnited", "field_70833_d", "bq");

    @Override
    public void effect(World world, BlockPos pos, TotemBase totem, int repetition, int horizontal, int vertical)
    {
        if(world.isRemote)
            return;

        try
        {
            for(Entity entity : EntityUtil.getEntitiesInRange(world, pos, horizontal, vertical))
            {
                if(entity instanceof EntityCreeper)
                {
                    int ignited = (Integer) timeSinceIgnited.get(entity);

                    if(repetition < 5)
                    {
                        Random random = new Random();
                        if(random.nextInt(4 + repetition + (totem.getTotemEffectMusic() / 16)) == 1)
                        {
                            return;
                        }
                    }

                    if(ignited > 20 - repetition)
                    {
                        timeSinceIgnited.setInt(entity, 0);
                        ((EntityCreeper)entity).setCreeperState(-1);
                    }
                }
            }
        }
        catch(IllegalAccessException e)
        {
            logger.error("Could not perform Ocelot Totem effect", e);
        }
    }

}



