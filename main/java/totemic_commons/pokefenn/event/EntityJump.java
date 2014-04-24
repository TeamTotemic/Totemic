package totemic_commons.pokefenn.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import totemic_commons.pokefenn.potion.ModPotions;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityJump
{

    @SubscribeEvent
    public void jumpHeight(LivingEvent.LivingJumpEvent event)
    {
        if(event.entityLiving instanceof EntityPlayer && event.entityLiving.isPotionActive(ModPotions.batPotion))
        {
            event.entityLiving.motionY += 0.2;
        }
    }
}


