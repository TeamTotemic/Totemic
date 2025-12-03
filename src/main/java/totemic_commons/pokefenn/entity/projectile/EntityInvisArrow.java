package totemic_commons.pokefenn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class EntityInvisArrow extends EntityArrow
{
    public EntityInvisArrow(World world)
    {
        super(world);
    }

    public EntityInvisArrow(World world, EntityLivingBase shooter, EntityLivingBase target, float velocity, float inaccuracy)
    {
        super(world, shooter, target, velocity, inaccuracy);
    }

    public EntityInvisArrow(World world, EntityLivingBase shooter, float velocity)
    {
        super(world, shooter, velocity);
    }

    // TODO: Apply slowing effect when shot by Baykok (might require copying the entirety of the EntityArrow.onUpdate() method, or otherwise a mixin)
}
