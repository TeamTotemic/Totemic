package totemic_commons.pokefenn.entity.projectile;

import cpw.mods.fml.common.registry.IThrowableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class EntityInvisArrow extends EntityArrow implements IThrowableEntity
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

    @Override
    public Entity getThrower()
    {
        return shootingEntity;
    }

    @Override
    public void setThrower(Entity entity)
    {
        this.shootingEntity = entity;
    }
}
