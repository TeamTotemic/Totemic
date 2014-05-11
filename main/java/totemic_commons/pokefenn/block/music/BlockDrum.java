package totemic_commons.pokefenn.block.music;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import totemic_commons.pokefenn.Totemic;
import totemic_commons.pokefenn.api.music.IMusic;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.network.block.music.DrumPacket;
import totemic_commons.pokefenn.tileentity.music.TileDrum;
import totemic_commons.pokefenn.util.TotemUtil;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockDrum extends BlockTileTotemic implements IMusic
{
    public BlockDrum()
    {
        super(Material.wood);
        setBlockName(Strings.DRUM_NAME);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileDrum();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        TileDrum tileDrum = (TileDrum) world.getTileEntity(x, y, z);

        if(!world.isRemote)
        {
            if(player.isSneaking())
            {
                TotemUtil.playMusicFromBlockForCeremonySelector(world, player, x, y, z, musicEnum(), 6);
                return true;
            }
            if(tileDrum.canPlay && !player.isSneaking())
            {
                tileDrum.canPlay = false;
                TotemUtil.playMusicForCeremony(tileDrum, this.musicEnum(), this.getRange(world, x, y, z, true, player), this.getMaximumMusic(world, x, y, z, true, player), this.getMusicOutput(world, x, y, z, true, player));
                //world.spawnParticle("note", (double) x + 0.5D, (double) y + 1.2D, (double) z + 0.5D, (double) 100 / 24.0D, 0.0D, 0.0D);
                world.markBlockForUpdate(x, y, z);
            }
        }

        return true;
    }

    @Override
    public MusicEnum musicEnum()
    {
        return MusicEnum.DRUM_MUSIC;
    }

    @Override
    public int getMaximumMusic(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 100;
    }

    @Override
    public int getMusicOutput(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 6;
    }

    @Override
    public int getRange(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 8;
    }

}
