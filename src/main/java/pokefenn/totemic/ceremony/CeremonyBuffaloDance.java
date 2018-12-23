package pokefenn.totemic.ceremony;

import java.util.stream.Stream;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.entity.animal.EntityBuffalo;
import pokefenn.totemic.util.EntityUtil;

public class CeremonyBuffaloDance extends Ceremony
{
    public CeremonyBuffaloDance(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        if(world.isRemote)
            return;

        getCows(world, pos, 8)
            .limit(2)
            .forEach(cow -> {
                EntityBuffalo buffalo = new EntityBuffalo(world);
                float health = cow.getHealth() / cow.getMaxHealth() * buffalo.getMaxHealth();
                buffalo.setHealth(health);
                buffalo.setGrowingAge(-24000);
                EntityUtil.spawnEntity(world, cow.posX, cow.posY, cow.posZ, buffalo);
                if(cow.getLeashed())
                    buffalo.setLeashHolder(cow.getLeashHolder(), true);
                cow.setDead();
                ((WorldServer) world).spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, cow.posX, cow.posY + 1.0, cow.posZ, 24, 0.6D, 0.5D, 0.6D, 1.0D);
            });
    }

    private static Stream<? extends EntityAnimal> getCows(World world, BlockPos pos, int range)
    {
        return TotemicEntityUtil.getEntitiesInRange(EntityCow.class, world, pos, range, range, entity -> !(entity instanceof EntityBuffalo));
    }
}
