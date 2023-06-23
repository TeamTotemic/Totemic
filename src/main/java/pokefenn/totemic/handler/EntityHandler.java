package pokefenn.totemic.handler;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import pokefenn.totemic.entity.Buffalo;

public final class EntityHandler {
    public static void onEntityJoin(EntityJoinWorldEvent event) {
        if(event.getEntity().getType() == EntityType.SKELETON) {
            Skeleton entity = (Skeleton) event.getEntity();
            entity.targetSelector.addGoal(10, new NearestAttackableTargetGoal<>(entity, Buffalo.class, true));
        }
    }
}
