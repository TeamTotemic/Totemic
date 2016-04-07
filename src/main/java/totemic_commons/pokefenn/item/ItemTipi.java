package totemic_commons.pokefenn.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.block.tipi.BlockTipi;
import totemic_commons.pokefenn.lib.Strings;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class ItemTipi extends ItemBlock
{
    public ItemTipi(Block block)
    {
        super(block);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TIPI_ITEM_NAME);
        setNoRepair();
        setMaxStackSize(1);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        if(block.getMaterial(state) != Material.ground && !block.getUnlocalizedName().contains("dirt") && !block.getUnlocalizedName().contains("grass"))
        {
            return EnumActionResult.FAIL;
        }

        final int height = 6;
        final int radius = 2;

        //Check if placeable
        for(int i = -radius; i <= radius; i++)
            for(int j = 1; j <= height; j++)
                for(int k = -radius; k <= radius; k++)
                {
                    BlockPos p = pos.add(i, j, k);
                    if(!world.getBlockState(p).getBlock().isReplaceable(world, p))
                        return EnumActionResult.FAIL;
                }

        EnumFacing dir = EnumFacing.fromAngle(player.rotationYaw);

        //Place dummy blocks
        for(int i = 0; i < 2; i++)
        {
            for(EnumFacing blockDir : EnumFacing.HORIZONTALS)
            {
                if(blockDir == dir.getOpposite())
                    continue;
                world.setBlockState(pos.add(blockDir.getDirectionVec()).up(i+1), ModBlocks.dummyTipi.getDefaultState(), 2);
            }
        }
        world.setBlockState(pos.up(4), ModBlocks.dummyTipi.getDefaultState(), 2);
        world.setBlockState(pos.up(5), ModBlocks.dummyTipi.getDefaultState(), 2);
        world.setBlockState(pos.up(6), ModBlocks.dummyTipi.getDefaultState(), 2);

        //Place Tipi block itself
        world.setBlockState(pos.up(), ModBlocks.tipi.getDefaultState().withProperty(BlockTipi.FACING, dir), 2);

        world.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, this.block.getStepSound().getPlaceSound(), SoundCategory.BLOCKS, (this.block.getStepSound().getVolume() + 1.0F) / 2.0F, this.block.getStepSound().getPitch() * 0.8F, true);
        stack.stackSize--;
        return EnumActionResult.SUCCESS;
    }
}
