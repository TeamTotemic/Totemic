package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.totem.ITotemEffect;
import totemic_commons.pokefenn.api.totem.TotemRegistry;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 27/01/14
 * Time: 19:01
 */
public class TotemEffectSquid implements ITotemEffect
{

    @Override
    public void effect(TileEntity totem, int socketAmount, TotemRegistry totemRegistry, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(totem.getWorldObj().getWorldTime() % 80L == 0)
        {
            for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, vertical))
            {
                if(entity instanceof EntityPlayer)
                {
                    TotemUtil.addPotionEffects((EntityPlayer) entity, 40, Potion.waterBreathing, 0, totemWoodBonus, repetitionBonus, melodyAmount);
                }
            }
        }
    }


}
