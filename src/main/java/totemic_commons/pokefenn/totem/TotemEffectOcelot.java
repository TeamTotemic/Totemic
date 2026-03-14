package totemic_commons.pokefenn.totem;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.totem.TotemEffect;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 27/01/14
 * Time: 14:27
 */
public class TotemEffectOcelot extends TotemEffect
{
    public TotemEffectOcelot(String modid, String baseName, int horizontal, int vertical, int tier)
    {
        super(modid, baseName, horizontal, vertical, tier);
    }

    @Override
    public void effect(World world, int x, int y, int z, int poleSize, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        if(world.isRemote)
            return;

        for(EntityCreeper creeper : world.getEntitiesWithinAABB(EntityCreeper.class, EntityUtil.getAABBAround(x, y, z, horizontal, vertical)))
        {
            if(creeper.timeSinceIgnited > 20 - repetitionBonus)
            {
                creeper.timeSinceIgnited = 0;
                creeper.setCreeperState(-1);
            }
        }
    }

}



