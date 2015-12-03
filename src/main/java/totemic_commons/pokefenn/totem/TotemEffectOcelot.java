package totemic_commons.pokefenn.totem;

import static totemic_commons.pokefenn.Totemic.logger;

import java.lang.reflect.Field;
import java.util.Random;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.TotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 27/01/14
 * Time: 14:27
 */
public class TotemEffectOcelot extends TotemEffect
{
    public TotemEffectOcelot(String modid, String baseName, int horizontal, int vertical, int tier)
    {
        super(modid, baseName, horizontal, vertical, tier);
    }

    private final Field timeSinceIgnited = ReflectionHelper.findField(EntityCreeper.class, "timeSinceIgnited", "field_70833_d", "bq");

    @Override
    public void effect(TileEntity totem, int poleSize, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(totem.getWorldObj().isRemote)
            return;

        try
        {
            for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, vertical))
            {
                if(entity instanceof EntityCreeper)
                {
                    int ignited = (Integer) timeSinceIgnited.get(entity);

                    if(repetitionBonus < 5)
                    {
                        Random random = new Random();
                        if(random.nextInt(4 + repetitionBonus + (melodyAmount / 16)) == 1)
                        {
                            return;
                        }
                    }

                    if(ignited > 20 - repetitionBonus)
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



