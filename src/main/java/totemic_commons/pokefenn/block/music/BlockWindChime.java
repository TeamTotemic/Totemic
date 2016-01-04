package totemic_commons.pokefenn.block.music;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.client.PacketSound;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockWindChime extends BlockTileTotemic
{
    public BlockWindChime()
    {
        super(Material.iron);
        setUnlocalizedName(Strings.WIND_CHIME_NAME);
        setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 1F, 0.8F);
        setHardness(1.5F);
    }

    @Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighbor)
    {
        breakStuffs(world, pos);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return world.isAirBlock(pos.down())
                && (world.isSideSolid(pos.up(), EnumFacing.DOWN) || world.getBlockState(pos.up()).getBlock().isLeaves(world, pos.up()));
    }

    public void breakStuffs(World world, BlockPos pos)
    {
        if(!world.isRemote)
        {
            if(!canPlaceBlockAt(world, pos))
            {
                world.setBlockToAir(pos);
                spawnAsEntity(world, pos, new ItemStack(this));
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileWindChime tileWindChime = (TileWindChime) world.getTileEntity(pos);

        if(!world.isRemote && player.isSneaking() && !tileWindChime.isPlaying() && tileWindChime.canPlay)
        {
            tileWindChime.canPlay = false;
            PacketHandler.sendAround(new PacketSound(pos, "windChime"), world.getTileEntity(pos));
            TotemUtil.playMusicForSelector(world, pos, HandlerInitiation.windChime, 0);
            TotemUtil.particlePacket(world, EnumParticleTypes.NOTE, pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5, 6, 0.0, 0.0, 0.0, 0.0);
            TotemUtil.particlePacket(world, EnumParticleTypes.FIREWORKS_SPARK, pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5, 6, 0.0, 0.0, 0.0, 0.0);
        }
        return true;
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
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileWindChime();
    }
}
