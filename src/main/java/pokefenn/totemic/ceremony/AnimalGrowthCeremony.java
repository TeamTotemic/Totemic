package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.util.MiscUtil;

public enum AnimalGrowthCeremony implements CeremonyInstance {
    INSTANCE;

    private static final int RADIUS = 6;

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(!level.isClientSide && context.getTime() % 20 == 0) {
            level.getEntities(EntityType.ITEM, TotemicEntityUtil.getAABBAround(pos, RADIUS), e -> e.getItem().is(Items.EGG))
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
    }

    @Override
    public int getEffectTime() {
        return 45 * 20;
    }
}
