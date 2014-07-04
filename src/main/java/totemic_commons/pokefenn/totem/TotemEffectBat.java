package totemic_commons.pokefenn.totem;

import totemic_commons.pokefenn.potion.ModPotions;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 13:54
 */
public class TotemEffectBat extends TotemEffectPotion
{

    public TotemEffectBat()
    {
        super(ModPotions.batPotion, 12 * 20, 120, 20);
    }

    /*
    @Override
    public void effect(TileEntity totem, int socketAmount, TotemRegistry totemRegistry, int horizontal, int vertical, int melodyAmount)
    {
        if(totem.getWorldObj().getWorldTime() % (12L * 20L) == 0L)
        {
            if(EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, vertical) != null)
            {
                for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, vertical))
                {
                    if(entity instanceof EntityPlayer)
                    {
                        TotemUtil.addPotionEffects((EntityPlayer) entity, 120, 20, ModPotions.batPotion, 0, false);
                    }

                }

            }
        }

    }
    */

}
