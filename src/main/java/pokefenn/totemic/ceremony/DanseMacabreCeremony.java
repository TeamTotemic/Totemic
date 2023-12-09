package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.util.MiscUtil;

public enum DanseMacabreCeremony implements CeremonyInstance {
    INSTANCE;

    private static final int RANGE = 6;

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(!level.isClientSide && context.getTime() % 20 == 0) {
            level.getEntities(EntityType.ITEM, TotemicEntityUtil.getAABBAround(pos, RANGE), e -> e.getItem().is(Items.ROTTEN_FLESH))
            .forEach(item -> {
                if(level.random.nextInt(4) == 0)
                    summonZombie(level, item);
            });
        }
    }

    private void summonZombie(Level level, ItemEntity item) {
        var pos = BlockPos.containing(item.position());
        if(!level.getBlockState(pos).getCollisionShape(level, pos).isEmpty() || !level.getBlockState(pos.above()).getCollisionShape(level, pos).isEmpty())
            return;

        LivingEntity zombie;
        if(level.dimension() == Level.NETHER)
            zombie = EntityType.ZOMBIFIED_PIGLIN.create(level);
        else if(level.random.nextInt(10) == 0)
            zombie = EntityType.ZOMBIE_VILLAGER.create(level);
        else if(level.getFluidState(pos).is(FluidTags.WATER))
            zombie = EntityType.DROWNED.create(level);
        else if(level.getBiome(pos).is(Biomes.DESERT))
            zombie = EntityType.HUSK.create(level);
        else
            zombie = EntityType.ZOMBIE.create(level);

        var dx = 0.25 * level.random.nextGaussian();
        var dz = 0.25 * level.random.nextGaussian();
        var yRot = 360.0F * level.random.nextFloat();
        zombie.moveTo(item.getX() + dx, item.getY(), item.getZ() + dz, yRot, item.getXRot());

        zombie.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60 * 20, 2));
        zombie.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 60 * 20, 2));

        MiscUtil.shrinkItemEntity(item);
        level.addFreshEntity(zombie);
        MiscUtil.spawnServerParticles(ParticleTypes.LARGE_SMOKE, level, zombie.getBoundingBox().getCenter(), 24, new Vec3(0.6, 0.5, 0.6), 0.0);
    }

    @Override
    public int getEffectTime() {
        return 45 * 20;
    }
}
