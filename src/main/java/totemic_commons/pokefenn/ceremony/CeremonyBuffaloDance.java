package totemic_commons.pokefenn.ceremony;

import java.util.List;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.util.EntityUtil;

public class CeremonyBuffaloDance extends Ceremony
{
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
                EntityUtil.spawnEntity(world, cow.posX, cow.posY, cow.posZ, buffalo);
                cow.setDead();
            });
    }

    private static List<? extends EntityAnimal> getCows(World world, BlockPos pos, int range)
    {
        if(!Loader.isModLoaded("animania"))
            return EntityUtil.getEntitiesInRange(EntityCow.class, world, pos, range, range, entity -> !(entity instanceof EntityBuffalo));
        else //Animania compatibility
            return EntityUtil.getEntitiesInRange(EntityAnimal.class, world, pos, range, range, entity ->
                  (entity instanceof EntityCow && !(entity instanceof EntityBuffalo))
                || entity.getClass().getName().startsWith("com.animania.entities.cows.EntityBull")
                || entity.getClass().getName().startsWith("com.animania.entities.cows.EntityCow"));
    }
}
