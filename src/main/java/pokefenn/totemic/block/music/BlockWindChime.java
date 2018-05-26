package pokefenn.totemic.block.music;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.init.ModContent;
import pokefenn.totemic.init.ModSounds;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.tileentity.music.TileWindChime;
import pokefenn.totemic.util.TotemUtil;

public class BlockWindChime extends Block implements ITileEntityProvider
{
    public BlockWindChime()
    {
        super(Material.IRON);
        setRegistryName(Strings.WIND_CHIME_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.WIND_CHIME_NAME);
        setHardness(1.5F);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
    {
        if(!world.isRemote && player.isSneaking())
            playSelector(world, pos, player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        tooltip.add(I18n.format(getUnlocalizedName() + ".tooltip"));
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block neighbor, BlockPos fromPos)
    {
        if(!canPlaceBlockAt(world, pos))
        {
            world.setBlockToAir(pos);
            spawnAsEntity(world, pos, new ItemStack(this));
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        IBlockState upState = world.getBlockState(pos.up());
        return world.isAirBlock(pos.down())
                && (world.isSideSolid(pos.up(), EnumFacing.DOWN) || upState.getBlock().isLeaves(upState, world, pos.up()));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote && player.isSneaking())
            playSelector(world, pos, player);
        return true;
    }

    private void playSelector(World world, BlockPos pos, EntityPlayer player)
    {
        TotemUtil.playSound(world, pos, ModSounds.windChime, SoundCategory.PLAYERS, 1.0f, 1.0f);
        Totemic.api.music().playSelector(world, pos, player, ModContent.windChime);
        ((WorldServer) world).spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5, 6, 0.0, 0.0, 0.0, 0.0);
        ((WorldServer) world).spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5, 6, 0.0, 0.0, 0.0, 0.0);
    }

    @Override
    public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param)
    {
        if(!world.isRemote)
            return true;

        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileWindChime)
            ((TileWindChime) tile).setPlaying(param);
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileWindChime)
            ((TileWindChime) tile).setNotPlaying();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 1F, 0.8F);
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

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileWindChime();
    }
}
