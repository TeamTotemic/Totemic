package pokefenn.totemic.ceremony;

import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicEntityTypeTags;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.entity.Buffalo;
import pokefenn.totemic.init.ModEntityTypes;
import pokefenn.totemic.util.MiscUtil;

public enum BuffaloDanceCeremony implements CeremonyInstance {
    INSTANCE;

    private static final int RANGE = 8;

    private static final Predicate<Mob> CAN_APPLY_BUFFALO_DANCE = mob -> mob.is(TotemicEntityTypeTags.BUFFALO_DANCE_TARGETS) && mob.isAlive();

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(level.isClientSide())
            return;

        level.getEntitiesOfClass(Mob.class, TotemicEntityUtil.getAABBAround(pos, RANGE), CAN_APPLY_BUFFALO_DANCE).stream()
        .limit(2)
        .forEach(cow -> {
            // TODO: Use the convertTo method instead?
            var buffalo = ModEntityTypes.buffalo.get().create(level, EntitySpawnReason.CONVERSION);
            if(buffalo == null)
                return;
            float health = cow.getHealth() / cow.getMaxHealth() * buffalo.getMaxHealth();
            buffalo.setHealth(health);
            buffalo.setAge(Buffalo.BABY_START_AGE);
            buffalo.copyPosition(cow);
            if(cow.isLeashed())
                buffalo.setLeashedTo(cow.getLeashHolder(), true);
            cow.discard();
            level.addFreshEntity(buffalo);
            MiscUtil.spawnServerParticles(ParticleTypes.HAPPY_VILLAGER, level, buffalo.position().add(0, 1, 0), 24, new Vec3(0.6, 0.5, 0.6), 1.0);
        });
    }

    @Override
    public boolean canSelect(Level level, BlockPos pos, Entity initiator) {
        if(level.getEntitiesOfClass(Mob.class, TotemicEntityUtil.getAABBAround(pos, RANGE), CAN_APPLY_BUFFALO_DANCE).isEmpty()) {
            if(initiator instanceof Player player)
                player.sendOverlayMessage(Component.translatable("totemic.noCowsNearby"));
            return false;
        }
        else
            return true;
    }
}
