package pokefenn.totemic.ceremony;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.music.MusicInstrument;

public class CeremonySunDance extends Ceremony
{
    private static final DamageSource SUN_DANCE_DMG = new DamageSource("totemic.sunDance").setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage();

    public CeremonySunDance(String name, int musicNeeded, int maxStartupTime, MusicInstrument... selectors)
    {
        super(name, musicNeeded, maxStartupTime, selectors);
    }

    @Override
    public void onStartup(World world, BlockPos pos, StartupContext context)
    {
        if(!world.isRemote && context.getTime() % 20 == 10)
        {
            TotemicEntityUtil.getPlayersInRange(world, pos, 8, 8)
                .filter(player -> player.getHealth() > 1)
                .forEach(player -> player.attackEntityFrom(SUN_DANCE_DMG, 1));
        }
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        if(!world.isRemote)
        {
            TotemicEntityUtil.getPlayersInRange(world, pos, 8, 8).forEach(player ->
            {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 15 * 20, 3));
                player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 5 * 60 * 20, 4));
            });
        }
    }
}
