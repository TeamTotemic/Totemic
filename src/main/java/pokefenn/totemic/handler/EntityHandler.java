package pokefenn.totemic.handler;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import pokefenn.totemic.entity.Buffalo;

public final class EntityHandler {
    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if(event.getEntity().getType() == EntityType.SKELETON) {
            Skeleton entity = (Skeleton) event.getEntity();
            entity.targetSelector.addGoal(10, new NearestAttackableTargetGoal<>(entity, Buffalo.class, true));
        }
    }
}
