package totemic_commons.pokefenn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityInvisArrow extends EntityArrow
{
    public EntityInvisArrow(World world)
    {
        super(world);
    }

    public EntityInvisArrow(World world, EntityLivingBase shooter, EntityLivingBase target, float velocity, float inaccuracy)
    {
        super(world);
        this.renderDistanceWeight = 10.0D;
        this.shootingEntity = shooter;

        if(shooter instanceof EntityPlayer)
            this.canBePickedUp = 1;

        this.posY = shooter.posY + shooter.getEyeHeight() - 0.1;
        double dx = target.posX - shooter.posX;
        double dy = target.getEntityBoundingBox().minY + target.height / 3.0F - this.posY;
        double dz = target.posZ - shooter.posZ;
        double xzdist = MathHelper.sqrt_double(dx*dx + dz*dz);

        if(xzdist >= 1.0E-7)
        {
            float yaw = (float)(MathHelper.atan2(dz, dx) * 180.0 / Math.PI) - 90.0F;
            float pitch = (float)(-(MathHelper.atan2(dy, xzdist) * 180.0 / Math.PI));
            double xdir = dx / xzdist;
            double zdir = dz / xzdist;
            this.setLocationAndAngles(shooter.posX + xdir, this.posY, shooter.posZ + zdir, yaw, pitch);
            double yoff = xzdist * 0.125; //lower y-offset than vanilla arrows for very fast arrows
            this.setThrowableHeading(dx, dy + yoff, dz, velocity, inaccuracy);
        }
    }
}
