package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.blessing.BlessingHandler;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyGhostDance extends Ceremony
{
    public CeremonyGhostDance(String modid, String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos)
    {
        if(world.isRemote)
            return;

        for(Entity entity : EntityUtil.getEntitiesInRange(world, pos, 8, 8))
        if(entity instanceof EntityPlayer)
        {
            BlessingHandler.increaseBlessing(2, ((EntityPlayer) entity).getName(), world, pos);
        }

    }
}
