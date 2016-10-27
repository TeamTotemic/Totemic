package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.util.EntityUtil;

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
            entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 * (60 * 3), 1));
            entity.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20 * (60 + 30), 1));
        }
    }
}
