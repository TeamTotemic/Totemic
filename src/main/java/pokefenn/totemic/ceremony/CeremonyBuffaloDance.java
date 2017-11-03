package pokefenn.totemic.ceremony;

import java.util.List;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Loader;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.entity.animal.EntityBuffalo;
import pokefenn.totemic.util.EntityUtil;

public class CeremonyBuffaloDance extends Ceremony
{
    private static final boolean ANIMANIA_LOADED = Loader.isModLoaded("animania");

    public CeremonyBuffaloDance(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, int time)
    {
        if(world.isRemote)
            return;

        getCows(world, pos, 8).stream()
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

    private static List<? extends EntityAnimal> getCows(World world, BlockPos pos, int range)
    {
        if(!ANIMANIA_LOADED)
            return EntityUtil.getEntitiesInRange(EntityCow.class, world, pos, range, range, entity -> !(entity instanceof EntityBuffalo));
        else //Animania compatibility
            return EntityUtil.getEntitiesInRange(EntityAnimal.class, world, pos, range, range, entity ->
                  (entity instanceof EntityCow && !(entity instanceof EntityBuffalo))
                || entity.getClass().getName().startsWith("com.animania.entities.cows.EntityBull")
                || entity.getClass().getName().startsWith("com.animania.entities.cows.EntityCow"));
    }
}
