package totemic_commons.pokefenn.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.util.EntityUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemTipi extends ItemTotemic
{
    public ItemTipi()
    {
        super(Strings.TIPI_ITEM_NAME);
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int thingy, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if(!world.isRemote)
        {
            //TODO if checks to see if it can actually place the tipi.

            MovingObjectPosition movingObjectPosition = EntityUtil.raytraceFromEntity(world, player, true, 3);
            Block block = EntityUtil.getBlockFromPosition(movingObjectPosition, world);

            boolean canPlace = true;

            int vertRadius = 6;
            int radius = 2;
            for(int i = -radius; i <= radius; i++)
                for(int j = 1; j <= vertRadius; j++)
                    for(int k = -radius; k <= radius; k++)
                    {
                        if(!world.isAirBlock(movingObjectPosition.blockX + i, movingObjectPosition.blockY + j, movingObjectPosition.blockZ + k))
                        {
                            canPlace = false;
                        }
                    }

            if(!canPlace)
                return false;

            if(block.getMaterial() == Material.ground || (block.getUnlocalizedName().contains("dirt") || block.getUnlocalizedName().contains("grass")))
            {
                int dir = MathHelper.floor_double((double) ((player.rotationYaw * 4F) / 360F) + 0.5D) & 3;
                world.setBlock(movingObjectPosition.blockX, movingObjectPosition.blockY + 4, movingObjectPosition.blockZ, ModBlocks.dummyTipi, 0, 2);
                world.setBlock(movingObjectPosition.blockX, movingObjectPosition.blockY + 5, movingObjectPosition.blockZ, ModBlocks.dummyTipi, 0, 2);
                world.setBlock(movingObjectPosition.blockX, movingObjectPosition.blockY + 6, movingObjectPosition.blockZ, ModBlocks.dummyTipi, 1, 2);

                for(int i = 0; i < 2; i++)
                {
                    for(ForgeDirection direction : ForgeDirection.values())
                    {
                        world.setBlock(x + direction.offsetX, y + i + 1, z + direction.offsetZ, ModBlocks.dummyTipi, 0, 2);
                        world.setBlockToAir(x, y + i + 1, z);
                    }
                    if(world.getBlock(x + getDirectionThingy(dir)[0], y + i + 1, z + getDirectionThingy(dir)[1]) == ModBlocks.dummyTipi)
                        world.setBlockToAir(x + getDirectionThingy(dir)[0], y + i + 1, z + getDirectionThingy(dir)[1]);
                }
                world.setBlock(movingObjectPosition.blockX, movingObjectPosition.blockY + 1, movingObjectPosition.blockZ, ModBlocks.tipi, dir, 2);
                itemStack.stackSize--;
            }
        }

        return false;
    }


    public int[] getDirectionThingy(int i)
    {
        int[] thingy = {0, 0};
        if(i == 0)
        {
            thingy[1] = -1;
        }
        if(i == 1)
        {
            thingy[0] = 1;
        }
        if(i == 2)
        {
            thingy[1] = 1;
        }

        if(i == 3)
        {
            thingy[0] = -1;
        }
        return thingy;
    }
}
