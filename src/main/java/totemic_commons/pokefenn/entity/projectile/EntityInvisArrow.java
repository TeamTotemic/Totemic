package totemic_commons.pokefenn.entity.projectile;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityInvisArrow extends EntityArrow
{
    //Only set when the shooter is a player
    private static final DataParameter<Integer> SHOOTER_DATAWATCHER = EntityDataManager.createKey(EntityInvisArrow.class, DataSerializers.VARINT    );

    @SideOnly(Side.CLIENT)
    private boolean shotByPlayer;

    public EntityInvisArrow(World world)
    {
        super(world);
    }

    public EntityInvisArrow(World world, EntityLivingBase shooter, EntityLivingBase target, float velocity, float inaccuracy)
    {
        //TODO: Copy code from EntityArrow
        super(world);
        //this.renderDistanceWeight = 10.0D;
        this.shootingEntity = shooter;

        if(shooter instanceof EntityPlayer)
        {
            this.canBePickedUp = PickupStatus.ALLOWED;
            dataWatcher.set(SHOOTER_DATAWATCHER, shooter.getEntityId());
        }

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

    public EntityInvisArrow(World world, EntityLivingBase shooter, float velocity)
    {
        super(world, shooter, velocity);

        if(shooter instanceof EntityPlayer)
        {
            dataWatcher.set(SHOOTER_DATAWATCHER, shooter.getEntityId());
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.register(SHOOTER_DATAWATCHER, 0);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key)
    {
        super.notifyDataManagerChange(key);
        if(key == SHOOTER_DATAWATCHER)
        {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if(dataWatcher.get(SHOOTER_DATAWATCHER) == player.getEntityId())
            {
                shotByPlayer = true;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean isShotByPlayer()
    {
        return shotByPlayer;
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return new ItemStack(Items.arrow);
    }
}
