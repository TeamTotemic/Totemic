package totemic_commons.pokefenn.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import totemic_commons.pokefenn.potion.ModPotions;

public class EntityFall
{

    @SubscribeEvent
    public void onLivingFall(LivingFallEvent entity)
    {
        if(entity.entityLiving instanceof EntityPlayer && entity.entityLiving.isPotionActive(ModPotions.batPotion.id))
        {
            entity.setCanceled(true);
        }
    }
}
