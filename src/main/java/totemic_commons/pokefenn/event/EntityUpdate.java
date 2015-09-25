package totemic_commons.pokefenn.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent;
import totemic_commons.pokefenn.potion.ModPotions;

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
            EntityPlayer player = (EntityPlayer) event.entityLiving;

            if(player.isPotionActive(ModPotions.batPotion) && player.isSneaking() && !player.onGround)
            {
                player.moveFlying(0.0F, 0.5F, 0.1F);
            }

            if(player.isPotionActive(ModPotions.spiderPotion))
            {
                PotionEffect pot = player.getActivePotionEffect(ModPotions.spiderPotion);
                if(pot.getDuration() > 10)
                {
                    if(player.isSneaking())
                        player.stepHeight = 0.50001F;
                    else
                        player.stepHeight = 1F;
                }
                else
                    player.stepHeight = 0.5F;

                climb(player);
            }
        }
    }

    /**
     * @author joshiejack
     */
    private void climb(EntityPlayer player)
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

}
