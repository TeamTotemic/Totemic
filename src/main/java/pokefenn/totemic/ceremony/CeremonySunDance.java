package pokefenn.totemic.ceremony;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pokefenn.totemic.api.ceremony.Ceremony;
import pokefenn.totemic.api.ceremony.CeremonyEffectContext;
import pokefenn.totemic.api.music.MusicInstrument;
import pokefenn.totemic.util.EntityUtil;

public class CeremonySunDance extends Ceremony
{
    public CeremonySunDance(String name, int musicNeeded, int maxStartupTime, MusicInstrument... selectors)
    {
        super(name, musicNeeded, maxStartupTime, selectors);
    }

    @Override
    public void effect(World world, BlockPos pos, CeremonyEffectContext context)
    {
        if(world.isRemote)
            return;

        for(EntityPlayer player : EntityUtil.getEntitiesInRange(EntityPlayer.class, world, pos, 8, 8))
        {
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 10 * 20, 3));
            player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 5 * 60 * 20, 3));
        }
    }
}
