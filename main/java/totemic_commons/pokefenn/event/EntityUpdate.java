package totemic_commons.pokefenn.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import totemic_commons.pokefenn.potion.ModPotions;

import java.util.ArrayList;

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
        EntityLivingBase entityLiving = event.entityLiving;
        double x = entityLiving.posX;
        double y = entityLiving.posY;
        double z = entityLiving.posZ;

        if(entityLiving instanceof EntityPlayer && event.entityLiving.isPotionActive(ModPotions.batPotion))
        {
            EntityPlayer player = (EntityPlayer) event.entityLiving;
        }

        if(entityLiving instanceof EntityPlayer && event.entityLiving.isPotionActive(ModPotions.spiderPotion))
        {
            //Code from joshiejack :)

            EntityPlayer player = (EntityPlayer) event.entityLiving;

            if(!player.isOnLadder())
            {
                final float factor = 0.15F;

                if(player.isSneaking())
                {
                    player.motionY = 0;
                    //player.motionX = 0;
                    //player.motionZ = 0;
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


        if(entityLiving instanceof EntityPlayer && event.entityLiving.isPotionActive(ModPotions.antidotePotion))
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

            //if (event.entityLiving.isPotionActive(Potion.wither))
            //{
            //    event.entityLiving.removePotionEffect(Potion.wither.id);
            //}

            if(event.entityLiving.isPotionActive(Potion.moveSlowdown))
            {
                event.entityLiving.removePotionEffect(Potion.moveSlowdown.id);
            }

        }

        //TODO remember to turn this back on

    }


}
