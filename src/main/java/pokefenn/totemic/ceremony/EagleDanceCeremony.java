package pokefenn.totemic.ceremony;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.init.ModEntityTypes;
import pokefenn.totemic.util.MiscUtil;

public enum EagleDanceCeremony implements CeremonyInstance {
    INSTANCE;

    private static final int RANGE = 8;

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(level.isClientSide)
            return;
        level.getEntities(EntityType.PARROT, TotemicEntityUtil.getAABBAround(pos, RANGE), EntitySelector.ENTITY_STILL_ALIVE).stream()
        .limit(2)
        .forEach(parrot -> {
            var eagle = ModEntityTypes.bald_eagle.get().create(level);
            eagle.setPos(parrot.position());
            if(parrot.isLeashed())
                eagle.setLeashedTo(parrot.getLeashHolder(), true);
            parrot.discard();
            level.addFreshEntity(eagle);
            MiscUtil.spawnServerParticles(ParticleTypes.HAPPY_VILLAGER, level, eagle.position().add(0, 1, 0), 24, new Vec3(0.6, 0.5, 0.6), 1.0);
        });
    }

    @Override
    public boolean canSelect(Level level, BlockPos pos, Entity initiator) {
        if(level.getEntities(EntityType.PARROT, TotemicEntityUtil.getAABBAround(pos, RANGE), EntitySelector.ENTITY_STILL_ALIVE).isEmpty()) {
            initiator.sendMessage(new TranslatableComponent("totemic.noParrotsNearby"), Util.NIL_UUID);
            return false;
        }
        else
            return true;
    }
}
