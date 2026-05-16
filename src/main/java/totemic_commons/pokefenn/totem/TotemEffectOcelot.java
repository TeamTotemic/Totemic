package totemic_commons.pokefenn.totem;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.totem.TotemEffectPotion;
import totemic_commons.pokefenn.potion.ModPotions;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 27/01/14
 * Time: 14:27
 */
public class TotemEffectOcelot extends TotemEffectPotion
{
    public TotemEffectOcelot(String modid, String baseName, int horizontal, int vertical, int tier)
    {
        super(modid, baseName, horizontal, vertical, tier, ModPotions.ocelotPotion, 80, 60, 0);
    }

    @Override
    public void effect(World world, int x, int y, int z, int poleSize, int horizontal, int vertical, int melodyAmount, int totemWoodBonus, int repetitionBonus)
    {
        super.effect(world, x, y, z, poleSize, horizontal, vertical, melodyAmount, totemWoodBonus, repetitionBonus);

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



