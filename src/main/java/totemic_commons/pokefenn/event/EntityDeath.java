package totemic_commons.pokefenn.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import totemic_commons.pokefenn.misc.TotemicDamageSource;
import totemic_commons.pokefenn.recipe.registry.TotemRegistry;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntityDeath
{

    @SubscribeEvent()
    public void onLivingDeath(LivingDeathEvent entity/*, float distance*/)
    {
    }
}
