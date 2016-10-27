package totemic_commons.pokefenn.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import totemic_commons.pokefenn.potion.ModPotions;

public class EntityFall
{
    @SubscribeEvent
    public void onLivingFall(LivingFallEvent event)
    {
        if(event.getEntityLiving() instanceof EntityPlayer && event.getEntityLiving().isPotionActive(ModPotions.batPotion))
        {
            event.setCanceled(true);
        }
    }
}
