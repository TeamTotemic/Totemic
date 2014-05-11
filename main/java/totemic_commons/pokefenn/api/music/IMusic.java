package totemic_commons.pokefenn.api.music;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by Pokefenn.
 * Licensed under MIT (If this is one of my Mods)
 */
public interface IMusic
{
    /**
     * @return Returns the musicEnum that the item or block uses
     */
    public MusicEnum musicEnum();

    /**
     * @param world The world the block or player resides in
     * @param x The xCoordinate of the block or player
     * @param y The yCoordinate of the block or player
     * @param z The zCoordinate of the block or player
     * @param isFromPlayer This is to say if it comes from a player, or if it happens automatically.
     * @param player The player that plays it, if it is not from a player, just put null.
     * @return Return the maximum amount of muic it can create!
     */
    public int getMaximumMusic(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player);

    /**
     * @param world The world the block or player resides in
     * @param x The xCoordinate of the block or player
     * @param y The yCoordinate of the block or player
     * @param z The zCoordinate of the block or player
     * @param isFromPlayer This is to say if it comes from a player, or if it happens automatically.
     * @param player The player that plays it, if it is not from a player, just put null.
     * @return How much music at a time will the instrument output
     */
    public int getMusicOutput(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player);

    /**
     * @param world The world the block or player resides in
     * @param x The xCoordinate of the block or player
     * @param y The yCoordinate of the block or player
     * @param z The zCoordinate of the block or player
     * @param isFromPlayer This is to say if it comes from a player, or if it happens automatically.
     * @param player The player that plays it, if it is not from a player, just put null.
     * @return The range of the instrument, the radius to be exact.
     */
    public int getRange(World world, int x, int y, int z, boolean isFromPlayer, EntityPlayer player);
}
