package pokefenn.totemic.handler;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pokefenn.totemic.entity.animal.EntityBuffalo;

public class EntitySpawn
{
    @SubscribeEvent
    public void onEntityJoin(EntityJoinWorldEvent event)
    {
        if(event.getEntity() instanceof EntitySkeleton)
        {
            EntitySkeleton entity = (EntitySkeleton)event.getEntity();
            entity.targetTasks.addTask(10, new EntityAINearestAttackableTarget<>(entity, EntityBuffalo.class, true));
        }
    }
}
