package totemic_commons.pokefenn.event;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import totemic_commons.pokefenn.util.TotemUtil;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityHurt
{

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onEntityHurt(LivingHurtEvent event)
    {
        Entity sourceDamageEntity = event.source.getSourceOfDamage();

        Random random = new Random();

        if(event.entityLiving instanceof EntityPlayer && sourceDamageEntity != null)
        {
            if(random.nextInt(3) == 1)
                if(TotemUtil.getArmourAmounts((EntityPlayer) event.entityLiving) == 4)
                    sourceDamageEntity.attackEntityFrom(DamageSource.generic, event.ammount % 2);
        }
    }

}
