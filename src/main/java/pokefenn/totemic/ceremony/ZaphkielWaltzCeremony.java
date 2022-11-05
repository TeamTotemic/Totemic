package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.IPlantable;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.util.MiscUtil;

public enum ZaphkielWaltzCeremony implements CeremonyInstance {
    INSTANCE;

    private static final int RADIUS = 6;

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(!level.isClientSide && context.getTime() % 20 == 0) {
            TotemicEntityUtil.getEntitiesInRange(ItemEntity.class, level, pos, RADIUS, RADIUS, e -> e.getItem().getItem() == Items.EGG)
            .forEach(egg -> {
                if(level.random.nextInt(4) == 0) {
                    var chicken = EntityType.CHICKEN.create(level);
                    chicken.setPos(egg.position());
                    chicken.setAge(AgeableMob.BABY_START_AGE);
                    level.addFreshEntity(chicken);
                    MiscUtil.shrinkItemEntity(egg);
                }
            });
        }

        if(context.getTime() % 10 == 0) {
            BlockPos.betweenClosedStream(new AABB(pos).inflate(RADIUS - 1))
            .forEach(p -> {
                var state = level.getBlockState(p);
                var block = state.getBlock();
                if((block instanceof IPlantable || block instanceof BonemealableBlock) && state.isRandomlyTicking()) {
                    if(level.random.nextInt(4) < 3) {
                        if(!level.isClientSide)
                            state.randomTick((ServerLevel) level, p, level.random);
                        else
                            spawnParticles(level, p);
                    }
                }
            });
        }
    }

    @Override
    public int getEffectTime() {
        return 45 * 20;
    }

    private void spawnParticles(Level level, BlockPos pos) {
        var rand = level.random;
        var vec = Vec3.atCenterOf(pos).add(rand.nextGaussian(), rand.nextGaussian() * 0.5, rand.nextGaussian());
        double ySpeed = rand.nextGaussian();
        level.addParticle(ParticleTypes.HAPPY_VILLAGER, vec.x, vec.y, vec.z, 0.0, ySpeed, 0.0);
    }
}
