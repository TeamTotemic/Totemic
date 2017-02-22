package totemic_commons.pokefenn.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUtil
{
    public static <T extends Entity> List<T> getEntitiesInRange(Class<? extends T> clazz, World world, double posX, double posY, double posZ, double horizontal, double vertical)
    {
        return world.getEntitiesWithinAABB(clazz, new AxisAlignedBB(posX - horizontal, posY - vertical, posZ - horizontal, posX + horizontal, posY + vertical, posZ + horizontal));
    }

    public static <T extends Entity> List<T> getEntitiesInRange(Class<? extends T> clazz, World world, double posX, double posY, double posZ, double horizontal, double vertical, @Nullable Predicate<? super T> filter)
    {
        return world.getEntitiesWithinAABB(clazz, new AxisAlignedBB(posX - horizontal, posY - vertical, posZ - horizontal, posX + horizontal, posY + vertical, posZ + horizontal));
    }

    public static <T extends Entity> List<T> getEntitiesInRange(Class<? extends T> clazz, World world, BlockPos pos, double horizontal, double vertical)
    {
        return world.getEntitiesWithinAABB(clazz, new AxisAlignedBB(pos).expand(horizontal - 1, vertical - 1, horizontal - 1));
    }

    public static <T extends Entity> List<T> getEntitiesInRange(Class<? extends T> clazz, World world, BlockPos pos, double horizontal, double vertical, @Nullable Predicate<? super T> filter)
    {
        return world.getEntitiesWithinAABB(clazz, new AxisAlignedBB(pos).expand(horizontal - 1, vertical - 1, horizontal - 1), filter);
    }

    public static <T extends TileEntity> List<T> getTileEntitiesInRange(Class<? extends T> clazz, World world, BlockPos pos, int horizontalRadius, int verticalRadius)
    {
        return getTileEntitiesIn(clazz, world, pos.add(-horizontalRadius, -verticalRadius, -horizontalRadius),
                pos.add(horizontalRadius + 1, verticalRadius + 1, horizontalRadius + 1));
    }

    //This method no longer exists in later Minecraft versions. Had to take it from Minecraft 1.8.9.
    @SuppressWarnings("unchecked")
    public static <T extends TileEntity> List<T> getTileEntitiesIn(Class<? extends T> clazz, World world, BlockPos min, BlockPos max)
    {
        List<T> list = new ArrayList<>();
        for(int x = (min.getX() & ~0x0F); x < max.getX(); x += 16)
            for(int z = (min.getZ() & ~0x0F); z < max.getZ(); z += 16) // & ~0xF Floors it by 16. Yay bitmath!
            {
                if(!world.isBlockLoaded(new BlockPos(x, 0, z), true))
                    continue; //Prevent loading extra chunks

                Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
                if(chunk != null && !chunk.isEmpty())
                {
                    for(TileEntity tile : chunk.getTileEntityMap().values())
                    {
                        if(clazz.isInstance(tile) && !tile.isInvalid())
                        {
                            BlockPos pos = tile.getPos();
                            if(pos.getX() >= min.getX() && pos.getY() >= min.getY() && pos.getZ() >= min.getZ() &&
                               pos.getX() <  max.getX() && pos.getY() <  max.getY() && pos.getZ() <  max.getZ())
                            {
                                list.add((T) tile);
                            }

                        }
                    }
                }
            }
        return list;
    }

    public static void spawnEntity(World world, double xPos, double yPos, double zPos, Entity entity)
    {
        entity.setPosition(xPos, yPos, zPos);
        world.spawnEntityInWorld(entity);
    }

    public static void dropItem(World world, double xPos, double yPos, double zPos, ItemStack itemStack)
    {
        EntityItem item = new EntityItem(world, xPos, yPos, zPos, itemStack);
        item.setDefaultPickupDelay();
        world.spawnEntityInWorld(item);
    }

    public static void dropItem(World world, double xPos, double yPos, double zPos, Item item)
    {
        dropItem(world, xPos, yPos, zPos, new ItemStack(item));
    }

    //Code from Vazkii who borrowed it from mDiyo
    public static RayTraceResult raytraceFromEntity(World world, Entity player, boolean stopOnLiquid, double range)
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
        return world.rayTraceBlocks(vec3, vec31, stopOnLiquid);

    }

    public static IBlockState getBlockFromPosition(RayTraceResult movingObjectPosition, World world)
    {
        return world.getBlockState(movingObjectPosition.getBlockPos());
    }

    /**
     * Returns the client player. Useful if loading the class EntityPlayerSP causes problems.
     */
    @SideOnly(Side.CLIENT)
    public static EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }

}
