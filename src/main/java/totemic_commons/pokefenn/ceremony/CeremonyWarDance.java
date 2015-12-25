package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.ceremony.Ceremony;
import totemic_commons.pokefenn.api.ceremony.CeremonyTime;
import totemic_commons.pokefenn.api.music.MusicInstrument;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyWarDance extends Ceremony
{
    public CeremonyWarDance(String modid, String name, int musicNeeded, int maxStartupTime, MusicInstrument... instruments)
    {
        super(modid, name, musicNeeded, maxStartupTime, instruments);
    }

    @Override
    public void effect(World world, int x, int y, int z)
    {
        if(world.isRemote)
            return;

        for(Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, 8, 8))
        {
            if(entity instanceof EntityPlayer)
            {
                ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 20 * (60 * 3), 1));
                ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 20 * (60 + 30), 1));
            }
        }
    }
}
