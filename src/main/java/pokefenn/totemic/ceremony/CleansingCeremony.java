package pokefenn.totemic.ceremony;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.UUID;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.util.MethodHandleUtil;

public class CleansingCeremony implements CeremonyInstance {
    private static final int RANGE = 8;

    private static final MethodHandle startConverting = MethodHandleUtil.findMethod(ZombieVillager.class, "m_34383_", MethodType.methodType(void.class, UUID.class, int.class));

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(level.isClientSide)
            return;

        try {
            var player = context.getInitiatingPlayer();
            var uuid = player != null ? player.getUUID() : null;
            for(var zombie : level.getEntities(EntityType.ZOMBIE_VILLAGER, new AABB(pos).inflate(RANGE - 1), z -> z.hasEffect(MobEffects.WEAKNESS))) {
                //This method ensures the player gets all the beneficial effects for curing Zombie Villagers
                startConverting.invokeExact(zombie, uuid, 1);
            }
        }
        catch(Throwable e) {
            throw new RuntimeException(e);
        }

        //TODO: Cure Zombified Piglins too
    }

    @Override
    public boolean canSelect(Level level, BlockPos pos, Entity initiator) {
        if(level.getEntities(EntityType.ZOMBIE_VILLAGER, new AABB(pos).inflate(RANGE - 1), z -> z.hasEffect(MobEffects.WEAKNESS)).isEmpty()) {
            initiator.sendSystemMessage(Component.translatable("totemic.noWeakenedZombieVillagersNearby"));
            return false;
        }
        else
            return true;
    }
}
