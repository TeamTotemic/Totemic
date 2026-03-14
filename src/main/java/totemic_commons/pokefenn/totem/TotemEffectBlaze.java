package totemic_commons.pokefenn.totem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
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
    public void effect(World world, int x, int y, int z, int poleSize, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(world.isRemote)
            return;

        if(world.getTotalWorldTime() % 60L == 0)
        {

            for(EntityPlayer player : world.getEntitiesWithinAABB(EntityPlayer.class, EntityUtil.getAABBAround(x, y, z, horizontal, vertical)))
            {
                if(player.isBurning())
                {
                    if(world.rand.nextBoolean())
                        player.heal(2);
                }

                Totemic.api.totemEffect().addPotionEffect(player, Potion.fireResistance, true, 50, 0, melodyAmount, totemWoodBonus, repetitionBonus);
            }
        }

    }

}
