package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import totemic_commons.pokefenn.api.ceremony.ICeremonyEffect;
import totemic_commons.pokefenn.tileentity.TileCeremonyIntelligence;
import totemic_commons.pokefenn.tileentity.TileTotemIntelligence;
import totemic_commons.pokefenn.totem.TotemUtil;
import totemic_commons.pokefenn.util.EntityUtil;

import java.util.Random;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyHarvestFeast implements ICeremonyEffect
{
    @Override
    public void effect(TileCeremonyIntelligence tileCeremonyIntelligence)
    {
        Random rand = new Random();

        if(EntityUtil.getEntitiesInRange(tileCeremonyIntelligence.getWorldObj(), tileCeremonyIntelligence.xCoord, tileCeremonyIntelligence.yCoord, tileCeremonyIntelligence.zCoord, 20, 20) != null)
        {

            for(Entity entity : EntityUtil.getEntitiesInRange(tileCeremonyIntelligence.getWorldObj(), tileCeremonyIntelligence.xCoord, tileCeremonyIntelligence.yCoord, tileCeremonyIntelligence.zCoord, 20, 20))
            {
                if(entity instanceof EntityPlayer)
                {
                    if(((EntityPlayer) entity).getFoodStats().getFoodLevel() < 10)
                    {
                        ((EntityPlayer) entity).getFoodStats().addStats(rand.nextInt(5), rand.nextInt(4));
                    }
                }
            }

        }
    }
}
