package pokefenn.totemic.ceremony;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.util.MiscUtil;

public enum CleansingCeremony implements CeremonyInstance {
    INSTANCE;

    private static final int RANGE = 8;

    @SuppressWarnings("deprecation")
    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(level.isClientSide)
            return;
        var aabb = TotemicEntityUtil.getAABBAround(pos, RANGE);
        for(var mob: level.getEntitiesOfClass(Mob.class, aabb, mob -> getConversionTarget(mob).isPresent() && mob.hasEffect(MobEffects.WEAKNESS))) {
            var targetType = getConversionTarget(mob).get();
            if(mob instanceof ZombieVillager zombieVillager && targetType == EntityType.VILLAGER) {
                //This method ensures the player gets all the beneficial effects for curing Zombie Villagers
                var uuid = context.getInitiatingPlayer().map(Player::getUUID).orElse(null);
                zombieVillager.startConverting(uuid, 1);
            }
            else {
                try {
                    var converted = mob.convertTo(targetType, true);
                    if(converted != null) {
                        converted.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
                        MiscUtil.spawnServerParticles(ParticleTypes.HAPPY_VILLAGER, level, converted.getBoundingBox().getCenter(), 10, new Vec3(0.6, 0.5, 0.6), 1.0);
                    }
                }
                catch(ClassCastException e) {
                    throw new IllegalStateException("Invalid conversion target '" + targetType.builtInRegistryHolder().getRegisteredName() + "' for the cleasing ceremony, must be a Mob entity type", e);
                }
            }
        }
    }

    @Override
    public boolean canSelect(Level level, BlockPos pos, Entity initiator) {
        if(level.getEntitiesOfClass(Mob.class, TotemicEntityUtil.getAABBAround(pos, RANGE),
                mob -> getConversionTarget(mob).isPresent() && mob.hasEffect(MobEffects.WEAKNESS)).isEmpty()) {
            initiator.sendSystemMessage(Component.translatable("totemic.noZombifiedMonstersNearby"));
            return false;
        }
        else
            return true;
    }

    private static Optional<EntityType<? extends Mob>> getConversionTarget(Mob mob) {
        return Totemic.platform().getCleansingCeremonyConversion(mob);
    }
}
