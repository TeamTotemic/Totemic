package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.recipe.TotemRegistry;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 25/01/14
 * Time: 22:34
 */
public class TotemEffectHorse implements ITotemEffect
{

    @Override
    public void effect(TileEntity totem, int socketAmount, boolean intelligence, TotemRegistry totemRegistry, int horizontal, int verticle)
    {
        if(totem.getWorldObj().getWorldTime() % 80L == 0)
        {
            if(EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, verticle) != null)
            {
                for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, verticle))
                {
                    if(entity instanceof EntityPlayer)
                    {
                        int j = TotemUtil.getArmourAmounts((EntityPlayer) entity);

                        TotemUtil.addPotionEffects((EntityPlayer) entity, 160, 35, Potion.moveSpeed, 0, false);

                        //((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 200 + (30 * j), TotemUtil.getStrength((EntityPlayer) entity)));

                        if(intelligence)
                        {
                            ((TileTotemBase) totem).decreaseChlorophyll(totemRegistry.getChlorophyllDecrement());
                        }

                    }
                }

            }
        }

    }


}


