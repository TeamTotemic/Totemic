package totemic_commons.pokefenn.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.lib.Strings;

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
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        Block block = world.getBlock(x, y, z);
        if(block.getMaterial() != Material.ground && !block.getUnlocalizedName().contains("dirt") && !block.getUnlocalizedName().contains("grass"))
        {
            return false;
        }

        int height = 6;
        int radius = 2;
        for(int i = -radius; i <= radius; i++)
            for(int j = 1; j <= height; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    if(!world.getBlock(x + i, y + j, z + k).isReplaceable(world, x + i, y + j, z + k))
                    {
                        return false;
                    }
                }

        int dir = MathHelper.floor_double((player.rotationYaw * 4F) / 360F + 0.5D) & 3;
        world.setBlock(x, y + 4, z, ModBlocks.dummyTipi, 0, 2);
        world.setBlock(x, y + 5, z, ModBlocks.dummyTipi, 0, 2);
        world.setBlock(x, y + 6, z, ModBlocks.dummyTipi, 1, 2);

        for(int i = 0; i < 2; i++)
        {
            for(ForgeDirection direction : ForgeDirection.values())
            {
                world.setBlock(x + direction.offsetX, y + i + 1, z + direction.offsetZ, ModBlocks.dummyTipi, 0, 2);
            }
            world.setBlockToAir(x, y + i + 1, z);
            int[] offsets = getDirectionOffsets(dir);
            if(world.getBlock(x + offsets[0], y + i + 1, z + offsets[1]) == ModBlocks.dummyTipi)
                world.setBlockToAir(x + offsets[0], y + i + 1, z + offsets[1]);
        }
        world.setBlock(x, y + 1, z, ModBlocks.tipi, dir, 2);
        itemStack.stackSize--;

        return true;
    }


    public int[] getDirectionOffsets(int i)
    {
        int[] offsets = {0, 0};
        if(i == 0)
        {
            offsets[1] = -1;
        }
        if(i == 1)
        {
            offsets[0] = 1;
        }
        if(i == 2)
        {
            offsets[1] = 1;
        }

        if(i == 3)
        {
            offsets[0] = -1;
        }
        return offsets;
    }
}
