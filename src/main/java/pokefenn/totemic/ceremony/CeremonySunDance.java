package pokefenn.totemic.ceremony;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.ceremony.StartupContext;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.util.EntityUtil;

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
            for(EntityPlayer player : EntityUtil.getEntitiesInRange(EntityPlayer.class, world, pos, 8, 8))
            {
                if(player.getHealth() > 1)
                    player.attackEntityFrom(SUN_DANCE_DMG, 1);
            }
        }
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        int radius = 8;

        if(!world.isRemote && context.getTime() % 20 == 0)
        {
            for(EntityPlayer player : EntityUtil.getEntitiesInRange(EntityPlayer.class, world, pos, radius, radius))
            {
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 10 * 20, 3));
                player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 5 * 60 * 20, 4));
            }
        }

        if(world.isRemote && context.getTime() % 5 == 0)
        {
            for(int i = -radius; i <= radius; i++)
                for(int j = -radius; j <= radius; j++)
                {
                    if(world.rand.nextBoolean())
                    {
                        BlockPos p = world.getTopSolidOrLiquidBlock(pos.add(i, 0, j));
                        spawnParticles(world, p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5);
                    }
                }
        }
    }

    @Override
    public int getEffectTime()
    {
        return VERY_SHORT;
    }

    private void spawnParticles(World world, double x, double y, double z)
    {
        double dx = world.rand.nextGaussian();
        double dy = world.rand.nextGaussian() * 0.5;
        double dz = world.rand.nextGaussian();
        double velY = world.rand.nextGaussian();
        world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x + dx, y + dy, z + dz, 0, velY, 0);
    }
}
