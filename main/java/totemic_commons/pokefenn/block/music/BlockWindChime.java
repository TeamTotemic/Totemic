package totemic_commons.pokefenn.block.music;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import totemic_commons.pokefenn.api.music.IMusic;
import totemic_commons.pokefenn.api.music.MusicEnum;
import totemic_commons.pokefenn.block.BlockTileTotemic;
import totemic_commons.pokefenn.lib.Strings;
import totemic_commons.pokefenn.tileentity.music.TileWindChime;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public class BlockWindChime extends BlockTileTotemic implements IMusic
{
    public BlockWindChime()
    {
        super(Material.wood);
        setBlockName(Strings.WIND_CHIME_NAME);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileWindChime();
    }

    @Override
    public MusicEnum musicEnum()
    {
        return MusicEnum.WIND_CHIME_MUSIC;
    }

    @Override
    public int getMaximumMusic(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 80;
    }

    @Override
    public int getMusicOutput(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 3;
    }

    @Override
    public int getRange(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player)
    {
        return 6;
    }
}
