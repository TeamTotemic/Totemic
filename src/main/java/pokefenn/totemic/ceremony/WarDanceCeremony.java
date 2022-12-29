package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;

public enum WarDanceCeremony implements CeremonyInstance {
    INSTANCE;

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(level.isClientSide)
            return;
        TotemicEntityUtil.getPlayersIn(level, TotemicEntityUtil.getAABBAround(pos, 8)).forEach(entity -> {
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * (60 * 3), 1), context.getInitiator().orElse(null));
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * (60 * 3), 1), context.getInitiator().orElse(null));
        });
    }
}
