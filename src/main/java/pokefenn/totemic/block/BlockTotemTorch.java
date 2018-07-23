package pokefenn.totemic.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.lib.Strings;

public class BlockTotemTorch extends Block
{
    public BlockTotemTorch()
    {
        super(Material.CIRCUITS); //Same material as vanilla torch
        setRegistryName(Strings.TOTEM_TORCH_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.TOTEM_TORCH_NAME);
        setLightLevel(1F);
        setSoundType(SoundType.WOOD);
        setHardness(0.05F);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        IBlockState below = world.getBlockState(pos.down());
        return below.getBlock().canPlaceTorchOnTop(below, world, pos.down());
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
        if(!canPlaceBlockAt(world, pos))
        {
            world.setBlockToAir(pos);
            spawnAsEntity(world, pos, new ItemStack(this));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        for(int i = 0; i < 2; i++)
            world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1F, pos.getZ() + 0.5, 0, 0, 0);
        for(int i = 0; i < 4; i++)
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 1F, pos.getZ() + 0.5, 0, 0, 0);
    }

    private static final AxisAlignedBB AABB = new AxisAlignedBB(4.75/16, 0.0, 4.75/16, 11.25/16, 1.0, 11.25/16);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return AABB;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return NULL_AABB;
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

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing facing)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
