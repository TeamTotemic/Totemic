package totemic_commons.pokefenn.totem;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ITotemEffect;
import totemic_commons.pokefenn.item.ItemTotemBeads;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 27/01/14
 * Time: 14:27
 */
public class TotemEffectOcelot implements ITotemEffect
{

    public static void effect(TileTotemic totem, int i, int upgrades, boolean intelligence)
    {
        if (totem.getWorldObj().getWorldTime() % 5L == 0)
        {
            if (EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)) != null)
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, 10 + (upgrades * 5), 10 + (upgrades * 5)))
                {
                    if (entity instanceof EntityCreeper)
                    {
                        int ignited = (Integer) ReflectionHelper.getPrivateValue(EntityCreeper.class, (EntityCreeper) entity, "timeSinceIgnited", "field_70833_d", "bq");

                        if (ignited > 17)
                        {

                            ReflectionHelper.setPrivateValue(EntityCreeper.class, (EntityCreeper) entity, 0, "timeSinceIgnited", "field_70833_d", "bq");

                            if(intelligence)
                            {
                                ((TileTotemIntelligence)totem).decreaseChlorophyll(TotemUtil.decrementAmount(i));
                            }

                        }
                    }
                }

            }
        }

    }


    public static void effectBead(EntityPlayer player, World world, ItemTotemBeads totemBeads, int i)
    {
        if (world.getWorldTime() % 10L == 0)
        {
            if (EntityUtil.getEntitiesInRange(world, player.posX, player.posY, player.posZ, 10, 10) != null)
            {

                for (Entity entity : EntityUtil.getEntitiesInRange(world, player.posX, player.posY, player.posZ, 10, 10))
                {
                    if (entity instanceof EntityCreeper)
                    {
                        int ignited = (Integer) ReflectionHelper.getPrivateValue(EntityCreeper.class, (EntityCreeper) entity, "timeSinceIgnited", "field_70833_d", "bq");

                        if (ignited > 17)
                        {

                            ReflectionHelper.setPrivateValue(EntityCreeper.class, (EntityCreeper) entity, 0, "timeSinceIgnited", "field_70833_d", "bq");

                            ItemTotemBeads.decreaseChlorophyll(player, TotemUtil.decrementAmount(i));

                        }
                    }
                }

            }
        }

    }


}
