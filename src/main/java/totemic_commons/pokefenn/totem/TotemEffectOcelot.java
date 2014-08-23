package totemic_commons.pokefenn.totem;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.totem.TotemRegistry;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 27/01/14
 * Time: 14:27
 */
public class TotemEffectOcelot implements ITotemEffect
{

    @Override
    public void effect(TileEntity totem, int socketAmount, TotemRegistry totemRegistry, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, vertical) != null)
        {
            for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, vertical))
            {
                if(entity instanceof EntityCreeper)
                {
                    int ignited = (Integer) ReflectionHelper.getPrivateValue(EntityCreeper.class, (EntityCreeper) entity, "timeSinceIgnited", "field_70833_d", "bq");

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
                        ReflectionHelper.setPrivateValue(EntityCreeper.class, (EntityCreeper) entity, 0, "timeSinceIgnited", "field_70833_d", "bq");
                    }
                }
            }

        }
    }

}



