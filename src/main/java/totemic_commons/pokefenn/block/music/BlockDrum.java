package totemic_commons.pokefenn.block.music;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.PacketHandler;
import totemic_commons.pokefenn.network.client.PacketDrumSound;
import totemic_commons.pokefenn.recipe.HandlerInitiation;
import totemic_commons.pokefenn.tileentity.music.TileDrum;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockDrum extends BlockTileTotemic
{
    public BlockDrum()
    {
        super(Material.wood);
        setBlockName(Strings.DRUM_NAME);
        setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.8F, 0.8F);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileDrum();
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
    {
        TileDrum tileDrum = (TileDrum) world.getTileEntity(x, y, z);

        if(!world.isRemote && tileDrum.canPlay)
            playDaMusicu(world, x, y, z, player, tileDrum, player.isSneaking());
    }

    public void playDaMusicu(World world, int x, int y, int z, EntityPlayer player, TileDrum tileDrum, boolean isSneaking)
    {
        if(!isSneaking)
        {
            if(!(player instanceof FakePlayer))
            {
                tileDrum.canPlay = false;
                TotemUtil.playMusicForCeremony(tileDrum, HandlerInitiation.drum, 0, 0);
                MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", (double) x + 0.5D, (double) y + 1.2D, (double) z + 0.5D, 6, 0.0D, 0.0D, 0.0D, 0.0D);
                world.markBlockForUpdate(x, y, z);
            }
        } else
        {
            tileDrum.canPlay = false;
            TotemUtil.playMusicFromBlockForCeremonySelector(world, x, y, z, HandlerInitiation.drum, 0);
            MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("note", (double) x + 0.5D, (double) y + 1.2D, (double) z + 0.5D, 6, 0.0D, 0.0D, 0.0D, 0.0D);
            MinecraftServer.getServer().worldServerForDimension(world.provider.dimensionId).func_147487_a("fireworksSpark", (double) x + 0.5D, (double) y + 1.2D, (double) z + 0.5D, 6, 0.0D, 0.0D, 0.0D, 0.0D);
            world.markBlockForUpdate(x, y, z);
        }

        PacketHandler.sendAround(new PacketDrumSound(x, y, z), world.getTileEntity(x, y, z));
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        TileDrum tileDrum = (TileDrum) world.getTileEntity(x, y, z);

        if(!world.isRemote)
        {
            if(tileDrum.canPlay)
            {
                playDaMusicu(world, x, y, z, player, tileDrum, player.isSneaking());
            }
        }

        return true;
    }

    /*
    @Override
    public int getMaximumMusic(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 75;
    }

    @Override
    public int getMusicOutput(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return world.getBlockMetadata(x, y, z) == 0 ? 6 : 12;
    }

    @Override
    public int getRange(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 5;
    }
    */

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

}
