package pokefenn.totemic.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pokefenn.totemic.init.ModPotions;

public class EntityUpdate
{

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
    {

        if(event.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();

            if(player.isPotionActive(ModPotions.batPotion) && !player.capabilities.isCreativeMode
                    && player.isSneaking() && !player.onGround)
            {
                player.moveRelative(0.0F, 0.0F, 0.5F, 0.1F);
                player.motionY /= 1.5;
            }

            if(player.isPotionActive(ModPotions.spiderPotion))
            {
                PotionEffect pot = player.getActivePotionEffect(ModPotions.spiderPotion);
                if(pot.getDuration() > 10)
                {
                    if(player.isSneaking())
                        player.stepHeight = 0.60001F;
                    else
                        player.stepHeight = 1F;
                }
                else
                    player.stepHeight = 0.6F;

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
        if(player.collidedHorizontally && !player.isOnLadder())
        {
            final float factor = 0.15F;

            if(player.isSneaking())
            {
                player.motionY = 0;
            }
            else
            {
                player.motionX = MathHelper.clamp(player.motionX, -factor, factor);
                player.motionY = 0.2;
                player.motionZ = MathHelper.clamp(player.motionZ, -factor, factor);

                player.fallDistance = 0.0F;
            }
        }
    }

}
