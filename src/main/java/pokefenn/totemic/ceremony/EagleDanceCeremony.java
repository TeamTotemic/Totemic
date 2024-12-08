package pokefenn.totemic.ceremony;

import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicEntityTypeTags;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.init.ModEntityTypes;
import pokefenn.totemic.util.MiscUtil;

public enum EagleDanceCeremony implements CeremonyInstance {
    INSTANCE;

    private static final int RANGE = 8;

    private static final Predicate<Mob> CAN_APPLY_EAGLE_DANCE = mob -> mob.getType().is(TotemicEntityTypeTags.EAGLE_DANCE_TARGETS) && mob.isAlive();

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(level.isClientSide)
            return;

        level.getEntitiesOfClass(Mob.class, TotemicEntityUtil.getAABBAround(pos, RANGE), CAN_APPLY_EAGLE_DANCE).stream()
        .limit(2)
        .forEach(parrot -> {
            var eagle = ModEntityTypes.bald_eagle.get().create(level);
            if(eagle == null)
                return;
            eagle.copyPosition(parrot);
            if(parrot.isLeashed())
                eagle.setLeashedTo(parrot.getLeashHolder(), true);
            parrot.discard();
            level.addFreshEntity(eagle);
            MiscUtil.spawnServerParticles(ParticleTypes.HAPPY_VILLAGER, level, eagle.position().add(0, 1, 0), 24, new Vec3(0.6, 0.5, 0.6), 1.0);
        });
    }

    @Override
    public boolean canSelect(Level level, BlockPos pos, Entity initiator) {
        if(level.getEntitiesOfClass(Mob.class, TotemicEntityUtil.getAABBAround(pos, RANGE), CAN_APPLY_EAGLE_DANCE).isEmpty()) {
            initiator.sendSystemMessage(Component.translatable("totemic.noParrotsNearby"));
            return false;
        }
        else
            return true;
    }
}
