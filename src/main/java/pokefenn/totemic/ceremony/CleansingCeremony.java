package pokefenn.totemic.ceremony;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.util.MethodHandleUtil;
import pokefenn.totemic.util.MiscUtil;

public class CleansingCeremony implements CeremonyInstance {
    private static final int RANGE = 8;

    private static final MethodHandle startConverting = MethodHandleUtil.findMethod(ZombieVillager.class, "m_34383_", MethodType.methodType(void.class, UUID.class, int.class));

    private static final Map<EntityType<? extends Mob>, EntityType<? extends Mob>> conversions = Map.of(
            EntityType.ZOMBIFIED_PIGLIN, EntityType.PIGLIN,
            EntityType.ZOGLIN, EntityType.HOGLIN,
            EntityType.ZOMBIE_HORSE, EntityType.HORSE);
    private static final Predicate<Mob> hasWeakness = m -> m.hasEffect(MobEffects.WEAKNESS);

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(level.isClientSide)
            return;
        var aabb = new AABB(pos).inflate(RANGE - 1);

        try {
            var player = context.getInitiatingPlayer();
            var uuid = player != null ? player.getUUID() : null;
            for(var zombieVillager : level.getEntities(EntityType.ZOMBIE_VILLAGER, aabb, hasWeakness)) {
                //This method ensures the player gets all the beneficial effects for curing Zombie Villagers
                startConverting.invokeExact(zombieVillager, uuid, 1);
            }
        }
        catch(Throwable e) {
            throw new RuntimeException(e);
        }

        for(var mob : level.getEntitiesOfClass(Mob.class, aabb, mob -> conversions.containsKey(mob.getType()) && hasWeakness.test(mob))) {
            var converted = mob.convertTo(conversions.get(mob.getType()), true);
            if(converted != null) {
                converted.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
                MiscUtil.spawnServerParticles(ParticleTypes.HAPPY_VILLAGER, level, converted.getBoundingBox().getCenter(), 10, new Vec3(0.6, 0.5, 0.6), 1.0);
            }
        }
    }

    @Override
    public boolean canSelect(Level level, BlockPos pos, Entity initiator) {
        if(level.getEntitiesOfClass(Mob.class, new AABB(pos).inflate(RANGE - 1),
                mob -> (conversions.containsKey(mob.getType()) || mob.getType() == EntityType.ZOMBIE_VILLAGER) && hasWeakness.test(mob)).isEmpty()) {
            initiator.sendSystemMessage(Component.translatable("totemic.noZombifiedMonstersNearby"));
            return false;
        }
        else
            return true;
    }
}
