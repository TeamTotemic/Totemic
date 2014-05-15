package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;
import totemic_commons.pokefenn.tileentity.totem.TileTotemBase;
import totemic_commons.pokefenn.tileentity.TileTotemic;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 25/01/14
 * Time: 21:42
 */
public class TotemEffectBlaze implements ITotemEffect
{

    @Override
    public void effect(TileTotemic totem, int upgrades, boolean intelligence, TotemRegistry totemRegistry, int horizontal, int verticle, int melody)
    {
        if(totem.getWorldObj().getWorldTime() % 80L == 0)
        {
            if(EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal + (upgrades * 5), verticle + (upgrades * 5)) != null)
            {
                for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal + (upgrades * 5), verticle + (upgrades * 5)))
                {
                    if(entity instanceof EntityPlayer)
                    {
                        if(entity.isBurning())
                        {
                            Random rand = new Random();
                            ((EntityPlayer) entity).heal(4 + rand.nextInt(TotemUtil.getArmourAmounts((EntityPlayer) entity)));
                        }

                        int j = TotemUtil.getArmourAmounts((EntityPlayer) entity);

                        TotemUtil.addPotionEffects((EntityPlayer) entity, 100, 40, Potion.fireResistance, 0, false);

                        //((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.fireResistance.id, 200 + (j * 30), 0));

                        if(intelligence)
                        {
                            ((TileTotemBase) totem).decreaseChlorophyll((totemRegistry.getChlorophyllDecrement() - melody > 50 ? 4 : 0));
                        }

                    }
                }

            }
        }

    }

}
