package totemic_commons.pokefenn.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import totemic_commons.pokefenn.potion.ModPotions;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityFall
{

    @SubscribeEvent()
    public void onLivingFall(LivingFallEvent entity/*, float distance*/)
    {
        if(entity.entityLiving instanceof EntityPlayer && entity.entityLiving.isPotionActive(ModPotions.batPotion.id))
        {
            entity.setCanceled(true);
        }
    }
}
