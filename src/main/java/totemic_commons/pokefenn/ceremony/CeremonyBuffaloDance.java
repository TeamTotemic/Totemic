package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.BlockPos;
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
    public void effect(World world, BlockPos pos)
    {
        if(world.isRemote)
            return;

        int buffalos = 0;
        for(EntityCow entity : EntityUtil.getEntitiesInRange(EntityCow.class, world, pos, 8, 8))
        {
            if(buffalos < 2)
            {
                if(!(entity instanceof EntityBuffalo))
                {
                    buffalos++;
                    EntityBuffalo buffalo = new EntityBuffalo(world);
                    float health = entity.getHealth() / entity.getMaxHealth() * buffalo.getMaxHealth();
                    buffalo.setHealth(health);
                    EntityUtil.spawnEntity(world, entity.posX, entity.posY, entity.posZ, buffalo);
                    entity.setDead();
                }
            }
        }
    }
}
