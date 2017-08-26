package pokefenn.totemic.block.music;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import pokefenn.totemic.ModContent;
import pokefenn.totemic.ModSounds;
import pokefenn.totemic.Totemic;
import pokefenn.totemic.lib.Strings;
import pokefenn.totemic.tileentity.music.TileDrum;
import pokefenn.totemic.util.TotemUtil;

public class BlockDrum extends Block implements ITileEntityProvider
{
    public BlockDrum()
    {
        super(Material.WOOD);
        setRegistryName(Strings.DRUM_NAME);
        setUnlocalizedName(Strings.RESOURCE_PREFIX + Strings.DRUM_NAME);
        setHardness(2);
        setSoundType(SoundType.WOOD);
        setCreativeTab(Totemic.tabsTotem);
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
    {
        TileDrum tileDrum = (TileDrum) world.getTileEntity(pos);

        if(!world.isRemote && tileDrum.canPlay)
            playDaMusicu((WorldServer) world, pos, player, tileDrum, player.isSneaking());
    }

    public void playDaMusicu(WorldServer world, BlockPos pos, EntityPlayer player, TileDrum tileDrum, boolean isSneaking)
    {
        IBlockState state = world.getBlockState(pos);
        if(!isSneaking)
        {
            if(!(player instanceof FakePlayer))
            {
                tileDrum.canPlay = false;
                TotemUtil.playMusic(world, pos, player, ModContent.drum, 0, 0);
                world.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 2, 0.0, 0.0, 0.0, 0.0);
                world.notifyBlockUpdate(pos, state, state, 7);
            }
        }
        else
        {
            tileDrum.canPlay = false;
            TotemUtil.playMusicForSelector(world, pos, player, ModContent.drum, 0);
            world.spawnParticle(EnumParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 2, 0.0, 0.0, 0.0, 0.0);
            world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 2, 0.0, 0.0, 0.0, 0.0);
            world.notifyBlockUpdate(pos, state, state, 7);
        }

        TotemUtil.playSound(world, pos, ModSounds.drum, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileDrum tileDrum = (TileDrum) world.getTileEntity(pos);

        if(!world.isRemote)
        {
            if(tileDrum.canPlay)
            {
                playDaMusicu((WorldServer)world, pos, player, tileDrum, player.isSneaking());
            }
        }

        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return new AxisAlignedBB(3F/16, 0F/16, 3F/16, 13F/16, 13F/16, 13F/16);
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
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileDrum();
    }
}
