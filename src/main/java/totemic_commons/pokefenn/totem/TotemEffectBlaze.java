package totemic_commons.pokefenn.totem;

import java.util.Random;

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
 * Time: 21:42
 */
public class TotemEffectBlaze extends TotemEffect
{

    public TotemEffectBlaze(String modid, String baseName, int vertical, int horizontal, int tier)
    {
        super(modid, baseName, horizontal, vertical, tier);
    }

    @Override
    public void effect(TileEntity totem, int poleSize, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(totem.getWorldObj().isRemote)
            return;

        if(totem.getWorldObj().getWorldTime() % 60L == 0)
        {

            for(Entity entity : EntityUtil.getEntitiesInRange(totem.getWorldObj(), totem.xCoord, totem.yCoord, totem.zCoord, getHorizontalRange(), getVerticalRange()))
            {
                if(entity instanceof EntityPlayer)
                {
                    if(entity.isBurning())
                    {
                        Random rand = new Random();
                        if(rand.nextBoolean())
                            ((EntityPlayer) entity).heal(2);
                    }

                    TotemUtil.addPotionEffects((EntityPlayer) entity, 40, Potion.fireResistance, 0, totemWoodBonus, repetitionBonus, melodyAmount);
                }
            }
        }

    }

}
