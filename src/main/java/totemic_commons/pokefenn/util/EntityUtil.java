package totemic_commons.pokefenn.util;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * Created with IntelliJ IDEA.
 * User: Pokefenn
 * Date: 20/01/14
 * Time: 14:22
 */
public class EntityUtil
{

    //Code from @WayofTime
    public static <T extends Entity> List<T> getEntitiesInRange(Class<? extends T> clazz, World world, double posX, double posY, double posZ, double horizontalRadius, double verticalRadius)
    {
        return world.getEntitiesWithinAABB(clazz, new AxisAlignedBB(posX - 0.5F, posY - 0.5f, posZ - 0.5f, posX + 0.5f, posY + 0.5f, posZ + 0.5f).expand(horizontalRadius, verticalRadius, horizontalRadius));
    }

    public static <T extends Entity> List<T> getEntitiesInRange(Class<? extends T> clazz, World world, BlockPos pos, double horizontalRadius, double verticalRadius)
    {
        return getEntitiesInRange(clazz, world, pos.getX(), pos.getY(), pos.getZ(), horizontalRadius, verticalRadius);
    }

    public static List<TileEntity> getTileEntitiesInRange(WorldServer world, BlockPos pos, int horizontalRadius, int verticalRadius)
    {
        return world.getTileEntitiesIn(pos.getX() - horizontalRadius, pos.getY() - verticalRadius, pos.getZ() - horizontalRadius,
                pos.getX() + horizontalRadius + 1, pos.getY() + verticalRadius + 1, pos.getZ() + horizontalRadius + 1);
    }

    public static void spawnEntity(World world, double xPos, double yPos, double zPos, Entity entity)
    {
        entity.setPosition(xPos, yPos, zPos);
        world.spawnEntityInWorld(entity);
    }

    public static void dropItem(World world, double xPos, double yPos, double zPos, ItemStack itemStack)
    {
        EntityItem item = new EntityItem(world, xPos, yPos, zPos, itemStack);
        item.setPickupDelay(10);
        world.spawnEntityInWorld(item);
    }

    public static void dropItem(World world, double xPos, double yPos, double zPos, Item item)
    {
        dropItem(world, xPos, yPos, zPos, new ItemStack(item));
    }

    //Code from Vazkii who borrowed it from mDiyo
    public static RayTraceResult raytraceFromEntity(World world, Entity player, boolean par3, double range)
    {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * f;
        if(!world.isRemote && player instanceof EntityPlayer)
            d1 += 1.62D;
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
        Vec3d vec3 = new Vec3d(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = range;
        if(player instanceof EntityPlayerMP)
            d3 = ((EntityPlayerMP) player).interactionManager.getBlockReachDistance();
        Vec3d vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
        return world.rayTraceBlocks(vec3, vec31, par3);

    }

    public static IBlockState getBlockFromPosition(RayTraceResult movingObjectPosition, World world)
    {
        return world.getBlockState(movingObjectPosition.getBlockPos());
    }

}
