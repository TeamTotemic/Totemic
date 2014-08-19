package totemic_commons.pokefenn.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
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

            if(block.getMaterial() == Material.ground || (block.getUnlocalizedName().contains("dirt") || block.getUnlocalizedName().contains("grass")))
            {
                int dir = MathHelper.floor_double((double) ((player.rotationYaw * 4F) / 360F) + 0.5D) & 3;
                world.setBlock(x, y + 6, z, ModBlocks.tipi, dir, 2);
                world.setBlock(x, y + 5, z, ModBlocks.dummyTipi, 0, 2);
                world.setBlock(x, y + 4, z, ModBlocks.dummyTipi, 0, 2);

                for(int i = 0; i < 3; i++)
                {
                    world.setBlock(x + 1, y + i + 1, z, ModBlocks.dummyTipi, 0, 2);
                    world.setBlock(x, y + i + 1, z + 1, ModBlocks.dummyTipi, 0, 2);
                    world.setBlock(x - 1, y + 1, z, ModBlocks.dummyTipi, 0, 2);
                    world.setBlock(x + 1, y + i + 1, z + 1, ModBlocks.dummyTipi, 0, 2);
                    world.setBlock(x - 1, y + i + 1, z - 1, ModBlocks.dummyTipi, 0, 2);
                    world.setBlock(x, y + i + 1, z - 1, ModBlocks.dummyTipi, 0, 2);
                    world.setBlock(x + 1, y + i + 1, z - 1, ModBlocks.dummyTipi, 0, 2);
                    world.setBlock(x - 1, y + i + 1, z + 1, ModBlocks.dummyTipi, 0, 2);
                    world.setBlock(x - 1, y + i + 1, z - 1, ModBlocks.dummyTipi, 0, 2);

                }
                itemStack.stackSize--;
            }
        }

        return false;
    }
}
