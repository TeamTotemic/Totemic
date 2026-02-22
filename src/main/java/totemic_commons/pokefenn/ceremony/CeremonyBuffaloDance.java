package totemic_commons.pokefenn.ceremony;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.AxisAlignedBB;
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
    public static final IEntitySelector selector = entity -> entity instanceof EntityCow && !(entity instanceof EntityBuffalo);

    public CeremonyBuffaloDance(String modid, String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, int x, int y, int z)
    {
        if(world.isRemote)
            return;

        int buffalos = 0;
        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5).expand(8, 8, 8);
        for(EntityLiving entity : world.selectEntitiesWithinAABB(EntityLiving.class, aabb, selector))
        {
            if(buffalos < 2)
            {
                buffalos++;
                EntityBuffalo buffalo = new EntityBuffalo(world);
                float health = entity.getHealth() / entity.getMaxHealth() * buffalo.getMaxHealth();
                buffalo.setHealth(health);
                EntityUtil.spawnEntity(world, entity.posX, entity.posY, entity.posZ, buffalo);
                if(entity.getLeashed())
                    buffalo.setLeashedToEntity(entity.getLeashedToEntity(), true);
                entity.setDead();
            }
        }
    }
}
