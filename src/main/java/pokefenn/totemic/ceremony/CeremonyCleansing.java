package pokefenn.totemic.ceremony;

import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.MobEffects;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.music.MusicInstrument;

public class CeremonyCleansing extends Ceremony
{
    public CeremonyCleansing(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        int radius = 6;

        if(!world.isRemote)
        {
            TotemicEntityUtil.getEntitiesInRange(EntityZombie.class, world, pos, radius, radius).forEach(zombie ->
            {
                if(zombie.isPotionActive(MobEffects.WEAKNESS))
                {
                    double x = zombie.posX;
                    double y = zombie.posY;
                    double z = zombie.posZ;

                    if(zombie instanceof EntityZombieVillager)
                    {
                        zombie.setDead();
                        ((WorldServer) world).spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x, y, z, 24, 0.6D, 0.5D, 0.6D, 1.0D);
                        EntityVillager villager = new EntityVillager(world);
                        villager.setPosition(x, y, z);
                        world.spawnEntity(villager);
                        return;
                    }
                    else if(zombie instanceof EntityPigZombie)
                    {
                        zombie.setDead();
                        ((WorldServer) world).spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x, y, z, 24, 0.6D, 0.5D, 0.6D, 1.0D);
                        EntityPig pig = new EntityPig(world);
                        pig.setPosition(x, y, z);
                        world.spawnEntity(pig);
                        return;
                    }
                }
            });
        }
    }
}
