package totemic_commons.pokefenn.event;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;
import totemic_commons.pokefenn.entity.projectile.EntityInvisArrow;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityHurt
{
    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event)
    {
        if(event.source.getSourceOfDamage() instanceof EntityInvisArrow)
        {
            if(!(event.entityLiving instanceof EntityBaykok))
                event.entityLiving.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 1));
        }
    }
}


