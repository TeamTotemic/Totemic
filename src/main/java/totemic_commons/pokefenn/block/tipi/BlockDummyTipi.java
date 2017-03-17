package totemic_commons.pokefenn.block.tipi;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import totemic_commons.pokefenn.ModBlocks;
import totemic_commons.pokefenn.lib.Strings;

public class BlockDummyTipi extends Block
{
    public BlockDummyTipi()
    {
        super(Material.CLOTH);
        setRegistryName(Strings.DUMMY_TIPI_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TIPI_NAME);
        setHardness(0.2F);
        setSoundType(SoundType.CLOTH);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        //find main Tipi block
        int range = 1;
        int height = 5;
        for(int i = -range; i <= range; i++)
            for(int j = 0; j >= -height; j--) //search downwards
                for(int k = -range; k <= range; k++)
                {
                    BlockPos p = pos.add(i, j, k);
                    IBlockState s = world.getBlockState(p);
                    if(s.getBlock() == ModBlocks.tipi)
                    {
                        ModBlocks.tipi.onBlockHarvested(world, p, s, player);
                        return;
                    }
                }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(ModBlocks.tipi);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state)
    {
        return EnumPushReaction.BLOCK;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
}
