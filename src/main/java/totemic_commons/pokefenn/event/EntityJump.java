package totemic_commons.pokefenn.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityJump
{

    @SubscribeEvent
    public void jumpHeight(LivingEvent.LivingJumpEvent event)
    {
        //if(event.entityLiving instanceof EntityPlayer && event.entityLiving.isPotionActive(ModPotions.batPotion))
        {
            // event.entityLiving.motionY += 0.2;
        }
    }
}


