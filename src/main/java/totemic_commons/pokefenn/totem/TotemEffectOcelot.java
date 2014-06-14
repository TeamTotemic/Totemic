package totemic_commons.pokefenn.totem;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 27/01/14
 * Time: 14:27
 */
public class TotemEffectOcelot implements ITotemEffect
{

    @Override
    public void effect(TileEntity totem, int socketAmount, TotemRegistry totemRegistry, int horizontal, int vertical, int melodyAmount)
    {
        if(totem.getWorldObj().getWorldTime() % 5L == 0)
        {
            if(EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, vertical) != null)
            {

                for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, vertical))
                {
                    if(entity instanceof EntityCreeper)
                    {
                        int ignited = (Integer) ReflectionHelper.getPrivateValue(EntityCreeper.class, (EntityCreeper) entity, "timeSinceIgnited", "field_70833_d", "bq");

                        if(ignited > 17)
                        {
                            ReflectionHelper.setPrivateValue(EntityCreeper.class, (EntityCreeper) entity, 0, "timeSinceIgnited", "field_70833_d", "bq");

                        }
                    }
                }

            }
        }

    }


}
