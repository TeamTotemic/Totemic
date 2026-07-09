package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.util.EntityUtil;

public class CeremonyDepths extends Ceremony
{
    public CeremonyDepths(String modid, String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, int x, int y, int z)
    {
        if(world.isRemote)
            return;

        for(EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class, EntityUtil.getAABBAround(x, y, z, 8, 8)))
        {
            player.addPotionEffect(new PotionEffect(Potion.waterBreathing.getId(), 20 * (60 * 3), 1));
        }
    }
}
