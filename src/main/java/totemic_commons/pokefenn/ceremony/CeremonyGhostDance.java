package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.ceremony.CeremonyTime;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.blessing.BlessingHandler;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyGhostDance extends Ceremony
{
    public CeremonyGhostDance(String modid, String name, int musicNeeded, CeremonyTime maxStartupTime, CeremonyTime effectTime,
            int musicPer5, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, effectTime, musicPer5, instruments);
    }

    @Override
    public void effect(World world, int x, int y, int z)
    {
        if(world.isRemote)
            return;

        for(Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, 8, 8))
        if(entity instanceof EntityPlayer)
        {
            BlessingHandler.increaseBlessing(2, ((EntityPlayer) entity).getDisplayName(), world, x, y, z);
        }

    }
}
