package totemic_commons.pokefenn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.util.EntityUtil;

public class EntityInvisArrow extends EntityArrow
{
    //Only set when the shooter is a player
    //FIXME: This is problematic and doesn't work
    private static final DataParameter<Integer> SHOOTER_DATAWATCHER = EntityDataManager.createKey(EntityInvisArrow.class, DataSerializers.VARINT);

    @SideOnly(Side.CLIENT)
    private boolean shotByPlayer;

    public EntityInvisArrow(World world)
    {
        super(world);
    }

    public EntityInvisArrow(World world, EntityLivingBase shooter)
    {
        super(world, shooter);
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
        if(worldObj.isRemote && key == SHOOTER_DATAWATCHER)
        {
            if(dataWatcher.get(SHOOTER_DATAWATCHER) == EntityUtil.getClientPlayer().getEntityId())
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
