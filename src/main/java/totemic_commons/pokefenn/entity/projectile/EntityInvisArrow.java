package totemic_commons.pokefenn.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import totemic_commons.pokefenn.entity.boss.EntityBaykok;
import totemic_commons.pokefenn.util.EntityUtil;

public class EntityInvisArrow extends EntityArrow
{
    private static final DataParameter<Integer> SHOOTING_PLAYER = EntityDataManager.createKey(EntityInvisArrow.class, DataSerializers.VARINT);

    @SideOnly(Side.CLIENT)
    private boolean shotByLocalPlayer;

    public EntityInvisArrow(World world)
    {
        super(world);
    }

    public EntityInvisArrow(World world, EntityLivingBase shooter)
    {
        super(world, shooter);
        if(shooter instanceof EntityPlayer)
        {
            dataManager.set(SHOOTING_PLAYER, shooter.getEntityId());
        }
    }

    @Override
    protected void arrowHit(EntityLivingBase living)
    {
        if(shootingEntity instanceof EntityBaykok)
            living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 1));
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataManager.register(SHOOTING_PLAYER, 0);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key)
    {
        super.notifyDataManagerChange(key);
        if(worldObj.isRemote && SHOOTING_PLAYER.equals(key))
        {
            if(dataManager.get(SHOOTING_PLAYER) == EntityUtil.getClientPlayer().getEntityId())
            {
                shotByLocalPlayer = true;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean isShotByLocalPlayer()
    {
        return shotByLocalPlayer;
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return new ItemStack(Items.ARROW);
    }
}
