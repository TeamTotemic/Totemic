package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.util.MiscUtil;

public enum AnimalGrowthCeremony implements CeremonyInstance {
    INSTANCE;

    private static final int RADIUS = 8;

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(!level.isClientSide && context.getTime() % 20 == 0) {
            var aabb = TotemicEntityUtil.getAABBAround(pos, RADIUS);

            level.getEntitiesOfClass(Animal.class, aabb, Animal::isBaby)
            .forEach(animal -> {
                if(level.random.nextInt(4) == 0) {
                    MiscUtil.spawnServerParticles(ParticleTypes.HAPPY_VILLAGER, level, animal.position(), 10, new Vec3(animal.getBbWidth(), animal.getBbHeight(), animal.getBbWidth()), 1.0);
                    animal.ageUp(level.random.nextInt(60));
                    //the argument to ageUp is given in seconds,
                    //this amounts to aging the animal up by about 337.5 s (out of 1200 s) each time the ceremony is used
                }
            });

            level.getEntities(EntityType.ITEM, aabb, e -> e.getItem().is(Items.EGG))
            .forEach(egg -> {
                if(level.random.nextInt(4) == 0) {
                    MiscUtil.spawnServerParticles(ParticleTypes.HAPPY_VILLAGER, level, egg.position(), 10, new Vec3(0.5, 0.5, 0.5), 1.0);
                    var chicken = EntityType.CHICKEN.create(level);
                    chicken.setPos(egg.position());
                    chicken.setAge(AgeableMob.BABY_START_AGE);
                    level.addFreshEntity(chicken);
                    MiscUtil.shrinkItemEntity(egg);
                }
            });
        }
    }

    @Override
    public int getEffectTime() {
        return 45 * 20;
    }
}
