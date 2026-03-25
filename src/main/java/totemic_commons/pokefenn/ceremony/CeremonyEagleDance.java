package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.configuration.ConfigurationSettings;
import totemic_commons.pokefenn.entity.animal.EntityBaldEagle;
import totemic_commons.pokefenn.util.EntityUtil;

public class CeremonyEagleDance extends Ceremony
{
    public CeremonyEagleDance(String modid, String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, int x, int y, int z)
    {
        if(world.isRemote)
            return;

        int eagles = 0;
        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(x - 0.5, y - 0.5, z - 0.5, x + 0.5, y + 0.5, z + 0.5).expand(8, 8, 8);
        for(EntityLiving entity : world.selectEntitiesWithinAABB(EntityLiving.class, aabb, CeremonyEagleDance::isValidTarget))
        {
            if(eagles < 2)
            {
                eagles++;
                EntityBaldEagle eagle = new EntityBaldEagle(world);
                float health = entity.getHealth() / entity.getMaxHealth() * eagle.getMaxHealth();
                eagle.setHealth(health);
                EntityUtil.spawnEntity(world, entity.posX, entity.posY, entity.posZ, eagle);
                if(entity.getLeashed())
                    eagle.setLeashedToEntity(entity.getLeashedToEntity(), true);
                entity.setDead();
            }
        }
    }

    private static boolean isValidTarget(Entity entity)
    {
        String entityId = EntityList.getEntityString(entity);
        return ConfigurationSettings.EAGLE_DANCE_TARGETS.contains(entityId);
    }
}
