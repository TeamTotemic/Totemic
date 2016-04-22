package totemic_commons.pokefenn.totem;

import java.util.Random;

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

    public TotemEffectBlaze(String name, int vertical, int horizontal)
    {
        super(name, horizontal, vertical);
    }

    @Override
    public void effect(World world, BlockPos pos, TotemBase totem, int repetition, int horizontal, int vertical)
    {
        if(world.isRemote)
            return;

        if(world.getTotalWorldTime() % 60L == 0)
        {
            for(EntityPlayer entity : EntityUtil.getEntitiesInRange(EntityPlayer.class, world, pos, horizontal, vertical))
            {
                if(entity.isBurning())
                {
                    Random rand = new Random();
                    if(rand.nextBoolean())
                        entity.heal(2);
                }

                Totemic.api.totemEffect().addPotionEffect(entity, Potion.fireResistance, 50, 0, totem, repetition);
            }
        }

    }

}
