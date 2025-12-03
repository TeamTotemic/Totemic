package totemic_commons.pokefenn.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;

public class EntityHurt
{
    @SubscribeEvent
    public void onEntityHurt(LivingHurtEvent event)
    {
        if(event.source.isProjectile() && event.source.getEntity() instanceof EntityBaykok)
        {
            event.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 1));
        }
    }
}
