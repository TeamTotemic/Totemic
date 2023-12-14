package pokefenn.totemic.ceremony;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.CeremonyInstance;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.init.ModResources;

public enum SunDanceCeremony implements CeremonyInstance {
    INSTANCE;

    private static final int RANGE = 8;

    @Override
    public void onStartup(Level level, BlockPos pos, StartupContext context) {
        if(!level.isClientSide && context.getTime() % 20 == 10) {
            var dmgSrc = new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ModResources.SUN_DANCE_DMG), pos.getCenter());
            TotemicEntityUtil.getPlayersIn(level, TotemicEntityUtil.getAABBAround(pos, RANGE), player -> !player.isSpectator() && player.getHealth() > 1)
                    .forEach(player -> player.hurt(dmgSrc, 1));
        }
    }

    @Override
    public void effect(Level level, BlockPos pos, CeremonyEffectContext context) {
        if(!level.isClientSide) {
            TotemicEntityUtil.getPlayersIn(level, TotemicEntityUtil.getAABBAround(pos, RANGE)).forEach(player -> {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 15 * 20, 3));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 5 * 60 * 20, 4));
            });
        }
    }
}
