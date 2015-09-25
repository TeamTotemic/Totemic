package totemic_commons.pokefenn.ceremony;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class CeremonyWarDance extends CeremonyBase
{
    @Override
    public void effect(TileEntity tileEntity)
    {

        for(Entity entity : EntityUtil.getEntitiesInRange(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, 8, 8))
        {
            if(entity instanceof EntityPlayer)
            {
                ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 20 * (60 * 3), 1));
                ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 20 * (60), 1));
            }
        }
    }
}
