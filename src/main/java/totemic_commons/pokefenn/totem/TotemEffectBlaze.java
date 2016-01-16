package totemic_commons.pokefenn.totem;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.totem.TotemBase;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;

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
    public void effect(World world, BlockPos pos, TotemBase totem, int horizontal, int vertical)
    {
        if(world.isRemote)
            return;

        if(world.getTotalWorldTime() % 60L == 0)
        {

            for(Entity entity : EntityUtil.getEntitiesInRange(world, pos, getHorizontalRange(), getVerticalRange()))
            {
                if(entity instanceof EntityPlayer)
                {
                    if(entity.isBurning())
                    {
                        Random rand = new Random();
                        if(rand.nextBoolean())
                            ((EntityPlayer) entity).heal(2);
                    }

                    Totemic.api.totemEffect().addPotionEffect((EntityPlayer) entity, Potion.fireResistance, 50, 0, totem);
                }
            }
        }

    }

}
