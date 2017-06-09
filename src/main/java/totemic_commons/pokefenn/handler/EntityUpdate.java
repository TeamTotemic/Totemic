package totemic_commons.pokefenn.handler;

import static totemic_commons.pokefenn.Totemic.logger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import totemic_commons.pokefenn.CapabilityMovementTracker;
import totemic_commons.pokefenn.CapabilityMovementTracker.MovementTracker;
import totemic_commons.pokefenn.ModPotions;

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
                player.moveRelative(0.0F, 0.5F, 0.1F);
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

            if(!player.world.isRemote)
            {
                MovementTracker tracker = player.getCapability(CapabilityMovementTracker.CAPABILITY, null);
                double vx = player.posX - tracker.getPrevPosX();
                double vy = player.posY - tracker.getPrevPosY();
                double vz = player.posZ - tracker.getPrevPosZ();
                Vec3d vec = new Vec3d(vx, vy, vz);
                double forward = vec.dotProduct(player.getLookVec());
                double total = Math.sqrt(vx*vx + vy*vy + vz*vz);
                logger.info("Total velocity: {}, Forward velocity: {}", total, forward);
                tracker.update(player);
            }
        }
    }

    /**
     * @author joshiejack
     */
    private void climb(EntityPlayer player)
    {
        //Code from joshiejack :)
        if(player.isCollidedHorizontally && !player.isOnLadder())
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
