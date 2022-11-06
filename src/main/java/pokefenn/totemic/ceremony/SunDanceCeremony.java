package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;

public enum SunDanceCeremony implements CeremonyInstance {
    INSTANCE;

    private static final DamageSource SUN_DANCE_DMG = new DamageSource("totemic.sunDance").bypassArmor().bypassMagic().setMagic();

    private static final int RANGE = 8;

    @Override
    public void onStartup(Level level, BlockPos pos, StartupContext context) {
        if(!level.isClientSide && context.getTime() % 20 == 10) {
            TotemicEntityUtil.getPlayersInRange(level, pos, RANGE, RANGE, player -> player.getHealth() > 1)
                    .forEach(player -> player.hurt(SUN_DANCE_DMG, 1));
        }
    }

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(!level.isClientSide) {
            TotemicEntityUtil.getPlayersInRange(level, pos, RANGE, RANGE).forEach(player -> {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 15 * 20, 3));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 5 * 60 * 20, 4));
            });
        }
    }
}
