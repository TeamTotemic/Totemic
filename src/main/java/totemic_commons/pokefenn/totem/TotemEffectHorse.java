package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.TotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 25/01/14
 * Time: 22:34
 */
public class TotemEffectHorse extends TotemEffect
{
    public TotemEffectHorse(String modid, String baseName, int horizontal, int vertical, int tier)
    {
        super(modid, baseName, horizontal, vertical, tier);
    }

    @Override
    public void effect(TileEntity totem, int poleSize, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(totem.getWorldObj().getWorldTime() % 80L == 0)
        {
            for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, getHorizontalRange(), getVerticalRange()))
            {
                if(entity instanceof EntityPlayer)
                {
                    TotemUtil.addPotionEffects((EntityPlayer) entity, 50, Potion.moveSpeed, 0, totemWoodBonus, repetitionBonus, melodyAmount);
                }
            }
        }

    }


}


