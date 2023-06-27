package pokefenn.totemic.totem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import pokefenn.totemic.api.TotemicAPI;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.totem.TotemEffect;
import pokefenn.totemic.api.totem.TotemEffectContext;
import pokefenn.totemic.util.MiscUtil;

public class OcelotTotemEffect extends TotemEffect {
    public OcelotTotemEffect() {
        super(10);
    }

    @Override
    public void effect(Level level, BlockPos pos, int repetition, TotemEffectContext context) {
        if(level.isClientSide)
            return;
        int range = TotemicAPI.get().totemEffect().getDefaultRange(repetition, context);
        for(Creeper creeper: level.getEntitiesOfClass(Creeper.class, TotemicEntityUtil.getAABBAround(pos, range))) {
            if(creeper.swell > 15) {
                creeper.swell = 0;
                creeper.setSwellDir(-1);
                MiscUtil.spawnServerParticles(ParticleTypes.INSTANT_EFFECT, creeper.level, creeper.getBoundingBox().getCenter(), 10, new Vec3(0.5, 0.75, 0.5), 0.0);
            }
        }
    }
}
