package totemic_commons.pokefenn.util;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
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
    public static List<Entity> getEntitiesInRange(World world, double posX, double posY, double posZ, double horizontalRadius, double verticalRadius)
    {
        return world.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(posX - 0.5F, posY - 0.5f, posZ - 0.5f, posX + 0.5f, posY + 0.5f, posZ + 0.5f).expand(horizontalRadius, verticalRadius, horizontalRadius));
    }

    public static List<TileEntity> getTileEntitiesIn(WorldServer world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        return world.func_147486_a(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static List<TileEntity> getTileEntitiesInRange(WorldServer world, int minX, int minY, int minZ, int horizontalRadius, int verticalRadius)
    {
        return world.func_147486_a(minX - horizontalRadius, minY - verticalRadius, minZ - horizontalRadius,
                minX + horizontalRadius, minY + verticalRadius, minZ + horizontalRadius);
    }

    public static void spawnEntity(World world, double xPos, double yPos, double zPos, Entity entity)
    {
        entity.setPosition(xPos, yPos, zPos);
        world.spawnEntityInWorld(entity);
    }

    public static void dropItem(World world, double xPos, double yPos, double zPos, ItemStack itemStack)
    {
        EntityItem item = new EntityItem(world, xPos, yPos, zPos, itemStack);
        item.delayBeforeCanPickup = 10;
        world.spawnEntityInWorld(item);
    }

    public static void dropItem(World world, double xPos, double yPos, double zPos, Item item)
    {
        dropItem(world, xPos, yPos, zPos, new ItemStack(item));
    }

    //Code from Vazkii who borrowed it from mDiyo
    public static MovingObjectPosition raytraceFromEntity(World world, Entity player, boolean par3, double range)
    {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * f;
        if(!world.isRemote && player instanceof EntityPlayer)
            d1 += 1.62D;
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = range;
        if(player instanceof EntityPlayerMP)
            d3 = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
        Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
        return world.rayTraceBlocks(vec3, vec31, par3);

    }

    public static Block getBlockFromPosition(MovingObjectPosition movingObjectPosition, World world)
    {
        return world.getBlock(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ);
    }

}
