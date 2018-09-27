package pokefenn.totemic.ceremony;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.TotemicEntityUtil;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.music.MusicInstrument;

public class CeremonyWarDance extends Ceremony
{
    public CeremonyWarDance(String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        if(world.isRemote)
            return;

        for(EntityPlayer entity : TotemicEntityUtil.getPlayersInRange(world, pos, 8, 8))
        {
            entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20 * (60 * 3), 1));
            entity.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20 * (60 * 3), 1));
        }
    }
}
