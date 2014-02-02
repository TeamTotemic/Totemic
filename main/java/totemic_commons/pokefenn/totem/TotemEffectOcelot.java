package totemic_commons.pokefenn.totem;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.tileentity.TileTotemBase;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 27/01/14
 * Time: 14:27
 */
public class TotemEffectOcelot implements ITotemEffect
{

    public static void effect(TileTotemBase totemBase)
    {

        int SLOT_TWO = TileTotemBase.SLOT_TWO;

        if (totemBase.getStackInSlot(SLOT_TWO).itemID == ModItems.chlorophyllCrystal.itemID)
        {
            if (EntityUtil.getEntitiesInRange(totemBase.worldObj, totemBase.xCoord, totemBase.yCoord, totemBase.zCoord, 10, 10) != null && !(totemBase.getStackInSlot(totemBase.SLOT_TWO).getMaxDamage() - totemBase.getStackInSlot(totemBase.SLOT_TWO).getItemDamage() - TileTotemBase.DECREASE_OCELOT <= 0))
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(totemBase.worldObj, totemBase.xCoord, totemBase.yCoord, totemBase.zCoord, 10, 10))
                {
                    if (entity instanceof EntityCreeper)
                    {
                        int i = (Integer) ReflectionHelper.getPrivateValue(EntityCreeper.class, (EntityCreeper) entity, "timeSinceIgnited", "field_70833_d", "bq");

                        if (i > 24)
                        {

                            ReflectionHelper.setPrivateValue(EntityCreeper.class, (EntityCreeper) entity, 0, "timeSinceIgnited", "field_70833_d", "bq");

                            totemBase.chlorophyllCrystalHandler(totemBase.DECREASE_OCELOT);
                        }
                    }
                }

            }
        }

    }


}
