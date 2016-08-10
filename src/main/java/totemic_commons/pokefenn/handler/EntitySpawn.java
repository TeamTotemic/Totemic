package totemic_commons.pokefenn.handler;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;

/**
 * @author ljfa
 */
public class EntitySpawn
{
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event)
    {
        if(event.entity instanceof EntitySkeleton)
        {
            EntitySkeleton entity = (EntitySkeleton)event.entity;
            entity.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(entity, EntityBuffalo.class, true));
        }
    }
}
