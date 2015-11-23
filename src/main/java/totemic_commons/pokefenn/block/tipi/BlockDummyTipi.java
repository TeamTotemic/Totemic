package totemic_commons.pokefenn.block.tipi;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.ModItems;
import totemic_commons.pokefenn.lib.Resources;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockDummyTipi extends Block
{
    public BlockDummyTipi()
    {
        super(Material.cloth);
        setBlockName(Strings.DUMMY_TIPI_NAME);
        setHardness(0.2F);
        setBlockTextureName(Resources.TEXTURE_LOCATION + ":" + Resources.DUMMY_TIPI);
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta,  EntityPlayer player)
    {
        int range = 2;
        int vertRange = 7;
        for(int i = -range; i <= range; i++)
            for(int j = -vertRange; j <= vertRange; j++)
                for(int k = -range; k <= range; k++)
                    if(world.getBlock(x + i, y + j, z + k) == ModBlocks.dummyTipi && world.getBlockMetadata(x + i, y + j, z + k) == 1)
                    {
                        world.setBlockToAir(x + i, y + j, z + k);
                        BlockDummyTipi.breakUnderTipi(world, x + i, y + j, z + k);
                        break;
                    }
    }

    public static void breakUnderTipi(World world, int x, int y, int z)
    {
        int vertRadius = 7;
        int radius = 2;
        for(int i = -radius; i <= radius; i++)
            for(int j = -vertRadius; j <= vertRadius; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    int n = j * -1;
                    Block block = world.getBlock(x + i, y + n, z + k);

                    if(block == ModBlocks.dummyTipi)
                    {
                        world.setBlockToAir(x + i, y + n, z + k);
                    }
                    else if(block == ModBlocks.tipi)
                    {
                        block.dropBlockAsItem(world, x + i, y + n, z + k, world.getBlockMetadata(x + i, y + n, z + k), 0);
                        world.setBlockToAir(x + i, y + n, z + k);
                    }
                }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World world, int x, int y, int z)
    {
        return ModItems.tipi;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

}
