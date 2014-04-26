package totemic_commons.pokefenn.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.LivingEvent;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.util.TotemUtil;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 08/03/14
 * Time: 17:52
 */
public class EntityUpdate
{

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {

        if(event.entityLiving instanceof EntityPlayer)
        {
            EntityLivingBase entityLiving = event.entityLiving;

            EntityPlayer player = (EntityPlayer) event.entityLiving;

            if(!player.worldObj.isRemote && player.worldObj.getWorldTime() % 60L == 0)
            {
                if(player.isPotionActive(Potion.wither))
                {
                    if(TotemUtil.getArmourAmounts(player) == 4)
                    {
                        Random rand = new Random();

                        if(rand.nextInt(10) == 1)
                        {
                            player.removePotionEffect(Potion.wither.id);
                        }
                    }
                } else if(player.isPotionActive(Potion.poison))
                {
                    if(TotemUtil.getArmourAmounts(player) == 4)
                    {
                        Random rand = new Random();

                        if(rand.nextInt(8) == 1)
                        {
                            player.removePotionEffect(Potion.poison.id);
                        }
                    }
                }
            }

            if(event.entityLiving.isPotionActive(ModPotions.spiderPotion))
            {
                //Code from joshiejack :)

                if(!player.isOnLadder())
                {
                    final float factor = 0.15F;

                    if(player.isSneaking() && player.isCollidedHorizontally)
                    {
                        player.motionY = 0;
                        return;
                    }

                    if(player.isCollidedHorizontally)
                    {

                        if(player.motionX < (-factor))
                        {
                            player.motionX = -factor;
                        }

                        if(player.motionX > factor)
                        {
                            player.motionX = factor;
                        }

                        if(player.motionZ < (-factor))
                        {
                            player.motionZ = -factor;
                        }

                        if(player.motionZ > factor)
                        {
                            player.motionZ = factor;
                        }

                        player.fallDistance = 0.0F;

                        if(player.motionY < -0.14999999999999999D)
                        {
                            player.motionY = -0.14999999999999999D;
                        }

                        player.motionY = 0.20000000000000001D;

                    }

                }
            }

            if(event.entityLiving.isPotionActive(ModPotions.antidotePotion))
            {
                if(event.entityLiving.isPotionActive(Potion.blindness))
                {
                    event.entityLiving.removePotionEffect(Potion.blindness.id);
                }

                if(event.entityLiving.isPotionActive(Potion.poison))
                {
                    event.entityLiving.removePotionEffect(Potion.poison.id);
                }

                if(event.entityLiving.isPotionActive(Potion.confusion))
                {
                    event.entityLiving.removePotionEffect(Potion.confusion.id);
                }

                if(event.entityLiving.isPotionActive(Potion.moveSlowdown))
                {
                    event.entityLiving.removePotionEffect(Potion.moveSlowdown.id);
                }

            }

        }
    }

}
