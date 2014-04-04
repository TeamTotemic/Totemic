package totemic_commons.pokefenn.entity.spirit;

import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.world.World;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class EntitySpiritOcelot extends EntitySpiritBase
{
    public EntitySpiritOcelot(World par1World)
    {
        super(par1World);
        targetTasks.addTask(1, null);
    }
}
