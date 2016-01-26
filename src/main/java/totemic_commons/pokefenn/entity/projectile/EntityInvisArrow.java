package totemic_commons.pokefenn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class EntityInvisArrow extends EntityArrow
{
    public EntityInvisArrow(World world, EntityLivingBase shooter, EntityLivingBase target, float velocity, float inaccuracy)
    {
        super(world, shooter, target, velocity, inaccuracy);
    }
}
