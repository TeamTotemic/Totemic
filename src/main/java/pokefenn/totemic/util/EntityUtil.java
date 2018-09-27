package pokefenn.totemic.util;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUtil
{
    public static <T extends Entity> List<T> getEntitiesInRange(Class<? extends T> clazz, World world, double posX, double posY, double posZ, double horizontal, double vertical, Predicate<? super T> filter)
    {
        return world.getEntitiesWithinAABB(clazz, new AxisAlignedBB(posX - horizontal, posY - vertical, posZ - horizontal, posX + horizontal, posY + vertical, posZ + horizontal), filter);
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
        world.spawnEntity(entity);
    }

    public static void dropItem(World world, double xPos, double yPos, double zPos, ItemStack itemStack)
    {
        EntityItem item = new EntityItem(world, xPos, yPos, zPos, itemStack);
        item.setDefaultPickupDelay();
        world.spawnEntity(item);
    }

    /**
     * Returns the client player. Useful if loading the class EntityPlayerSP causes problems.
     */
    @SideOnly(Side.CLIENT)
    public static EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().player;
    }

}
