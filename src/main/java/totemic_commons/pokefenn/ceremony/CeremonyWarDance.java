package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyWarDance extends Ceremony
{
    public CeremonyWarDance(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, int time)
    {
        if(world.isRemote)
            return;

        for(EntityPlayer entity : EntityUtil.getEntitiesInRange(EntityPlayer.class, world, pos, 8, 8))
        {
            entity.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 20 * (60 * 3), 1));
            entity.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 20 * (60 + 30), 1));
        }
    }
}
