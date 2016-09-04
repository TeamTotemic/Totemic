package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.entity.animal.EntityBuffalo;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
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

        EntityUtil.getEntitiesInRange(EntityCow.class, world, pos, 8, 8).stream()
            .filter(entity -> !(entity instanceof EntityBuffalo))
            .limit(2)
            .forEach(entity -> {
                EntityBuffalo buffalo = new EntityBuffalo(world);
                float health = entity.getHealth() / entity.getMaxHealth() * buffalo.getMaxHealth();
                buffalo.setHealth(health);
                EntityUtil.spawnEntity(world, entity.posX, entity.posY, entity.posZ, buffalo);
                entity.setDead();
            });
    }
}
