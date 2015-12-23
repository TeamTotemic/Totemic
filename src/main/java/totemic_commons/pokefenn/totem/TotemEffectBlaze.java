package totemic_commons.pokefenn.totem;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
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
    public void effect(World world, int x, int y, int z, int poleSize, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(world.isRemote)
            return;

        if(world.getWorldTime() % 60L == 0)
        {

            for(Entity entity : EntityUtil.getEntitiesInRange(world, x, y, z, getHorizontalRange(), getVerticalRange()))
            {
                if(entity instanceof EntityPlayer)
                {
                    if(entity.isBurning())
                    {
                        Random rand = new Random();
                        if(rand.nextBoolean())
                            ((EntityPlayer) entity).heal(2);
                    }

                    TotemUtil.addPotionEffects((EntityPlayer) entity, 50, Potion.fireResistance, 0, totemWoodBonus, repetitionBonus, melodyAmount);
                }
            }
        }

    }

}
