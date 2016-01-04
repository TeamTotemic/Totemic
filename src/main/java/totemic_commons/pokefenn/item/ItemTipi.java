package totemic_commons.pokefenn.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        Block block = world.getBlockState(pos).getBlock();
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
                    BlockPos p = pos.add(i, j, k);
                    if(!world.getBlockState(p).getBlock().isReplaceable(world, p))
                        return false;
                }

        EnumFacing dir = EnumFacing.fromAngle(player.rotationYaw);
        world.setBlockState(pos.up(4), ModBlocks.dummyTipi.getDefaultState(), 2);
        world.setBlockState(pos.up(5), ModBlocks.dummyTipi.getDefaultState(), 2);
        world.setBlockState(pos.up(6), ModBlocks.dummyTipi.getDefaultState()/* meta 1 FIXME*/, 2);

        for(int i = 0; i < 2; i++)
        {
            for(EnumFacing direction : EnumFacing.HORIZONTALS)
            {
                world.setBlockState(pos.add(direction.getDirectionVec()).up(i+1), ModBlocks.dummyTipi.getDefaultState(), 2);
            }
            world.setBlockToAir(pos.up(i+1));
            if(world.getBlockState(pos.add(dir.getDirectionVec()).up(i+1)).getBlock() == ModBlocks.dummyTipi)
                world.setBlockToAir(pos.add(dir.getDirectionVec()).up(i+1));
        }
        world.setBlockState(pos.up(), ModBlocks.tipi.getDefaultState()/*meta FIXME*/, 2);
        stack.stackSize--;

        return true;
    }
}
