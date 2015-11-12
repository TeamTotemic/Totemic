package totemic_commons.pokefenn.totem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import totemic_commons.pokefenn.api.TotemEffect;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.util.EntityUtil;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 23/01/14
 * Time: 13:54
 */
public class TotemEffectBat extends TotemEffect
{
    public TotemEffectBat(String modid, String baseName, int vertical, int horizontal, int tier)
    {
        super(modid, baseName, horizontal, vertical, tier);
    }

    @Override
    public void effect(TileEntity totem, int poleSize, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(totem.getWorldObj().getWorldTime() % 10L == 0L)
        {
            for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, horizontal, vertical))
            {
                if(entity instanceof EntityPlayer)
                {
                    TotemUtil.addPotionEffects((EntityPlayer) entity, 20, ModPotions.batPotion, 0, totemWoodBonus, repetitionBonus, melodyAmount);
                }

            }
        }

    }


}
