package pokefenn.totemic.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pokefenn.totemic.init.ModPotions;

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
