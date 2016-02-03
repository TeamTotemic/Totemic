package totemic_commons.pokefenn.ceremony;

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
    public CeremonyGhostDance(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, int time)
    {
        if(world.isRemote)
            return;

        for(EntityPlayer entity : EntityUtil.getEntitiesInRange(EntityPlayer.class, world, pos, 8, 8))
            BlessingHandler.increaseBlessing(2, entity.getName(), world, pos);

    }
}
