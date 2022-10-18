package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.entity.Buffalo;
import pokefenn.totemic.init.ModEntityTypes;
import pokefenn.totemic.util.MiscUtil;

public class BuffaloDanceCeremony extends CeremonyInstance {
    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(level.isClientSide)
            return;

        TotemicEntityUtil.getEntitiesInRange(Cow.class, level, pos, 8, 8, e -> e.getType() != ModEntityTypes.buffalo.get())
        .limit(2)
        .forEach(cow -> {
            var buffalo = new Buffalo(ModEntityTypes.buffalo.get(), level);
            float health = cow.getHealth() / cow.getMaxHealth() * buffalo.getMaxHealth();
            buffalo.setHealth(health);
            buffalo.setAge(Buffalo.BABY_START_AGE);
            buffalo.setPos(cow.position());
            if(cow.isLeashed())
                buffalo.setLeashedTo(cow.getLeashHolder(), true);
            cow.discard();
            level.addFreshEntity(buffalo);
            MiscUtil.spawnServerParticles(ParticleTypes.HAPPY_VILLAGER, level, buffalo.position().add(0, 1, 0), 24, new Vec3(0.6, 0.5, 0.6), 1.0);
        });
    }
}
