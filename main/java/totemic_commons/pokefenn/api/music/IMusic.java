package totemic_commons.pokefenn.api.music;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public interface IMusic
{
    public MusicEnum musicEnum();

    public int getMaximumMusic(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player);

    public int getMusicOutput(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player);

    public int getRange(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player);
}
